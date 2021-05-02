import model.dao.syslog.BaseDao
import model.{Histogram, Syslog}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.event.{LoggingAdapter, NoLogging}
import org.scalatest._

import java.sql.Timestamp
import java.util.Date
import scala.concurrent.Await
import scala.concurrent.duration._

trait BaseServiceSpec extends WordSpec with Matchers with ScalatestRouteTest with Routes with BaseDao {
  protected val log: LoggingAdapter = NoLogging

  import driver.api._

  val testSyslogs = Seq(
    Syslog(Some(1),  "n3", new Timestamp(new Date().getTime)),
    Syslog(Some(2),  "n1", new Timestamp(new Date().getTime)),
    Syslog(Some(3),  "n2", new Timestamp(new Date().getTime))
  )

  Await.result(sysLogTable ++= testSyslogs, 10.seconds)
}
