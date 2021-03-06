import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import model.dao.syslog.SyslogsDao
import service.{Config, LoadSyslogs}

import scala.concurrent.ExecutionContext


object Main extends App with Config with Routes {

  private implicit val system: ActorSystem = ActorSystem()
  protected implicit val executor: ExecutionContext = system.dispatcher
  val log: LoggingAdapter = Logging(system, getClass)

  SyslogsDao.setupDB()
  LoadSyslogs.loadSyslogs.map(res => log.info(s"System logs are parsed and inserted." ))

  val bindingFuture = Http()
    .bindAndHandle(handler = logRequestResult("log")(routes)
      , interface = httpInterface, port = httpPort)

  println(s"Server online at $httpInterface:$httpPort\nPress RETURN to stop...")
  scala.io.StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}


