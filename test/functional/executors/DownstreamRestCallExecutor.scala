package functional.executors

import controllers.CorrelationId
import functional.framework.RestCallExecutor
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future
import scala.beans.BeanProperty

class DownstreamRestCallExecutor extends RestCallExecutor {

  @BeanProperty var correlationId: String = _

  override def execute(wsClient: WSClient, port: Int): Future[WSResponse] = {
    val request = wsClient.url(s"http://localhost:$port/downstream")

    Option(correlationId)
      .map(id => request.addHttpHeaders(CorrelationId.CORRELATION_ID_HEADER_NAME -> id))
      .getOrElse(request)
      .get()
  }
}
