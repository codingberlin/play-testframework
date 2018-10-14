package controllers

import java.util.UUID

import play.api.mvc.RequestHeader

case class CorrelationId(id: String) {
  def asHeader: (String, String) =
    CorrelationId.CORRELATION_ID_HEADER_NAME -> id
}

object CorrelationId {
  val CORRELATION_ID_HEADER_NAME = "correlation-id"

  def fromRequest(request: RequestHeader): CorrelationId =
    CorrelationId(
      request.headers
        .get(CORRELATION_ID_HEADER_NAME)
        .getOrElse(UUID.randomUUID().toString))

}
