package mocks

import framework.Mock

import scala.beans.BeanProperty

import com.github.tomakehurst.wiremock.client.WireMock._

class DownstreamMock extends Mock {

  @BeanProperty var responseBody: String = _

  override def setupStubs(): Unit = {
    stubFor(
      get(urlEqualTo("/downstream")).willReturn(
        aResponse
          .withStatus(200)
          .withBody(responseBody)))
  }
}
