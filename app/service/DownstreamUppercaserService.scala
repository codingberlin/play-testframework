package service

import controllers.CorrelationId
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

class DownstreamUppercaserService @Inject()(downstreamClient: DownstreamClient) {

  def uppercaseDownstream(implicit correlationId: CorrelationId, executionContext: ExecutionContext): Future[String] = {
    downstreamClient.callDownstream
      .map(_.toUpperCase)
  }
}
