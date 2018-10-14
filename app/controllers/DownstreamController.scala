package controllers

import javax.inject._
import play.api.libs.ws.WSClient
import play.api.mvc._
import service.DownstreamUppercaserService

import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal

class DownstreamController @Inject()(wsClient: WSClient,
                                     downstreamUppercaserService: DownstreamUppercaserService,
                                     cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  def downstream(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    request.headers

    implicit val correlationId: CorrelationId = CorrelationId.fromRequest(request)

    downstreamUppercaserService.uppercaseDownstream
      .map(Ok(_))
      .recover {
        case NonFatal(e) =>
          ServiceUnavailable
      }
  }
}
