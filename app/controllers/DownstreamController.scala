package controllers

import javax.inject._
import play.api.libs.ws.WSClient
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global

class DownstreamController @Inject()(wsClient: WSClient,
                                     cc: ControllerComponents)
    extends AbstractController(cc) {

  def downstream(): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      wsClient
        .url("http://localhost:9000/downstream")
        .get()
        .map(_.body)
        .map(_.toUpperCase)
        .map(Ok(_))

  }
}
