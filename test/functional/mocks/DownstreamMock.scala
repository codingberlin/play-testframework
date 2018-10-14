package functional.mocks

import com.github.tomakehurst.wiremock.client.{MappingBuilder, RequestPatternBuilder}
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.http.Fault
import controllers.CorrelationId
import functional.framework.Mock
import functional.mocks.DownstreamMock._
import play.api.http.Status._

import scala.beans.BeanProperty

class DownstreamMock extends Mock {

  @BeanProperty var responseBody: String = _
  @BeanProperty var simulateNetworkFailure: Boolean = false
  @BeanProperty var correlationId: String = _

  override def setupStubs(): Unit = {
    val response = if (simulateNetworkFailure) {
      aResponse()
        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)
    } else {
      aResponse
        .withStatus(OK)
        .withBody(responseBody)
    }

    stubFor(request.willReturn(response))
  }

  private def request: MappingBuilder = {
    val mappingBuilder = get(urlEqualTo(URL))

    Option(correlationId)
      .map(id => mappingBuilder.withHeader(CorrelationId.CORRELATION_ID_HEADER_NAME, equalTo(id)))
      .getOrElse(mappingBuilder)
  }

  override def verifyInvocations(): Unit = {
    val request = getRequestedFor(urlEqualTo(URL))

    val enhancedRequest = Option(correlationId)
      .map(id => request.withHeader(CorrelationId.CORRELATION_ID_HEADER_NAME, equalTo(id)))
      .getOrElse(request)

    verify(enhancedRequest)
  }

}

object DownstreamMock {
  val URL = "/downstream"
}
