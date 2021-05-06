package api

import akka.event.LoggingAdapter
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Rejection, Route}
import de.heikoseeberger.akkahttpjackson.JacksonSupport
import model._
import model.dao.syslog.SyslogsDao

import scala.concurrent.ExecutionContext.Implicits.global

trait RejectionHandler extends (Seq[Rejection] ⇒Option[Route])

trait SyslogsApi extends JacksonSupport with ApiDataHandler {

  def log: LoggingAdapter

  val syslogsRoute: Route = Route.seal(
    (path("get_status") & get) {
      log.info("server is up and running")
      complete(checkStatus())
    } ~
      (path("get_size") & get) {
        complete(getRowSize(SyslogsDao.size))
      } ~
      (path("histogram") & post) {
        entity(as[ReqData]) { reqData =>
          complete(SyslogsDao.histogram(reqData).map(histos => generateHistogramFromDbHistoMapper(reqData, histos)))
        }
      } ~
      (path("data") & post) {
        entity(as[ReqData]) { reqData =>
          complete(SyslogsDao.data(reqData).map(syslogs => generateDataFromSyslogSeq(reqData, syslogs)))
        }
      } ~
      (path("logs") & get) {
        complete(SyslogsDao.findAll())
      }
  )
}




