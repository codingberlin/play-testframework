package service

import controllers.CorrelationId
import javax.inject.Inject
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}
import DownstreamClient._

class DownstreamClient @Inject()(wsClient: WSClient) {

  def callDownstream(implicit correlationId: CorrelationId, executionContext: ExecutionContext): Future[String] = {
    wsClient
      .url(DOWNSTREAM_URL)
      .addHttpHeaders(correlationId.asHeader)
      .get()
      .map(_.body)
  }
}

object DownstreamClient {
  val DOWNSTREAM_URL = "http://localhost:9000/downstream"
}
