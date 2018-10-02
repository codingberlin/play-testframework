package executors

import framework.RestCallExecutor
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

class DownstreamRestCallExecutor extends RestCallExecutor {
  override def execute(wsClient: WSClient, port: Int): Future[WSResponse] = {
    wsClient.url(s"http://localhost:$port/downstream").get()
  }
}
