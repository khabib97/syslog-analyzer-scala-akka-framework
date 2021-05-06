package api

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.ExceptionHandler

trait ApiErrorHandler {

  implicit def customExceptionHandler: ExceptionHandler = ExceptionHandler {
    case _: ArithmeticException  =>
      extractUri { uri =>
        complete(HttpResponse(InternalServerError, entity = s"Invalid Request ${uri}"))
      }
  }
}
