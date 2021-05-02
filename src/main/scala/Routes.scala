import api.{ApiErrorHandler, SyslogsApi}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

trait Routes extends ApiErrorHandler with  SyslogsApi{
  val routes: Route =
    pathPrefix("api") {
      syslogsRoute
    }

}
