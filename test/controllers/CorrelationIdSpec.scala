package controllers

import java.util.UUID

import org.scalatestplus.play._
import org.scalatest.mockito.MockitoSugar
import play.api.mvc.{Headers, RequestHeader}
import org.mockito.Mockito._
import org.mockito.Matchers.{eq => eqTo}

class CorrelationIdSpec extends PlaySpec with MockitoSugar {

  "CorrelationId" should {
    "generate UUID as correlation Id if none exists in the request" in {
      val request = mock[RequestHeader]
      when(request.headers).thenReturn(Headers())

      val correlationId = CorrelationId.fromRequest(request)

      try {
        UUID.fromString(correlationId.id)
        succeed
      } catch {
        case e: IllegalArgumentException =>
          fail("generated correlation id was no UUID", e)
      }
    }

    "extract correlation id from header" in {
      val request = mock[RequestHeader]
      when(request.headers).thenReturn(Headers("correlation-id" -> "42"))

      CorrelationId.fromRequest(request) mustBe CorrelationId("42")
    }
  }

}
