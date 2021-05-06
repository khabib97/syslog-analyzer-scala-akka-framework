import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import api.{ApiErrorHandler, SyslogsApi}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._


trait Routes extends ApiErrorHandler with SyslogsApi{

  val routes: Route = cors() {
    pathPrefix("api") {
      syslogsRoute
    }
  }

}
