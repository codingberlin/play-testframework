package service

import controllers.CorrelationId
import javax.inject.Inject
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}
import play.api.Configuration

class DownstreamClient @Inject()(config: Configuration, wsClient: WSClient) {

  val baseUrl = config.get[String]("downstreamBaseUrl")
  val endpointUrl = s"$baseUrl/downstream"

  def callDownstream(implicit correlationId: CorrelationId, executionContext: ExecutionContext): Future[String] = {
    wsClient
      .url(endpointUrl)
      .addHttpHeaders(correlationId.asHeader)
      .get()
      .map(_.body)
  }
}
