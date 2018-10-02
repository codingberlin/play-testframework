package framework

import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

trait RestCallExecutor {

  def execute(wsClient: WSClient, port: Int): Future[WSResponse]

}
