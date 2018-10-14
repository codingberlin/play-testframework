package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class PingController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def ping(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    NoContent
  }
}
