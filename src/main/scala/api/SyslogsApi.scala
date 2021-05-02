package api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpjackson.JacksonSupport
import model._
import model.dao.syslog.SyslogsDao
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global

trait SyslogsApi extends JacksonSupport with ApiDataHandler {

  val syslogsRoute: Route =
    (path("get_status") & get) {
      complete(JsObject("status" -> JsString("ok")))
    } ~
      (path("get_size") & get) {
        complete(SyslogsDao.size().map(size => JsObject("size" -> JsNumber(size))))
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
}

