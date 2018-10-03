package mocks

import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.http.Fault
import framework.Mock
import mocks.DownstreamMock._
import play.api.http.Status._

import scala.beans.BeanProperty

class DownstreamMock extends Mock {

  @BeanProperty var responseBody: String = _
  @BeanProperty var simulateNetworkFailure: Boolean = false

  override def setupStubs(): Unit = {
    if (simulateNetworkFailure) {
      stubFor(
        get(urlEqualTo(URL)).willReturn(aResponse()
          .withFault(Fault.MALFORMED_RESPONSE_CHUNK)))
    } else {
      stubFor(
        get(urlEqualTo(URL)).willReturn(
          aResponse
            .withStatus(OK)
            .withBody(responseBody)))
    }
  }
}

object DownstreamMock {
  val URL = "/downstream"
}
