package model.dao.syslog

import model.{DbHistogramMapper, ReqData, Syslog}
import slick.jdbc.GetResult
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.Future

object SyslogsDao extends BaseDao {

  implicit val getResult = GetResult(r => DbHistogramMapper(r.nextTimestamp(), r.nextString()))

  /** Bulk limit tested for 1 Million entry, application works fine.
   *  But for testing, 100K is good enough
   */
  val BULK_LIMIT: Int = 100000

  def setupDB() = {
    val setup = DBIO.seq(
      /** Create the tables, including primary and foreign keys */
      (sysLogTable.schema).create
    )
    db.run(setup)
  }

  def insertBulk(logs: List[Syslog]) = {
    val batch = logs.take(BULK_LIMIT)
    db.run(sysLogTable ++= batch)
    logs.drop(BULK_LIMIT)
  }

  def size: Future[Int] = {
    db.run(sysLogTable.length.result)
  }

  def data(data: ReqData): Future[Seq[Syslog]] =  {
      sysLogTable
        .filter(_.datetime >= data.datetimeFrom)
        .filter(_.datetime <= data.datetimeUntil)
        .filter(_.message like s"%${data.phrase}%")
        .result
  }

  def histogram(data: ReqData) = {
    /**Need to write raw query because there is no support for GROUP_CONCAT in slick*/
    val query = sql"""SELECT "date" AS date, GROUP_CONCAT("message") AS message FROM "logs" WHERE "date" >= ${data.datetimeFrom} AND "date" <= ${data.datetimeUntil} GROUP BY "date" """.as[DbHistogramMapper]
    db.run(query)
  }

  def findAll(): Future[Seq[Syslog]] = sysLogTable.result

}
