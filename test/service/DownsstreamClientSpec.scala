package service

import functional.framework.WireMockSupport
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.test.WsTestClient
import com.github.tomakehurst.wiremock.client.WireMock._
import controllers.CorrelationId
import org.apache.http.HttpStatus

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class DownsstreamClientSpec extends PlaySpec with MockitoSugar with WireMockSupport {

  val SOME_BODY = "some body"
  implicit val SOME_CORRELATION_ID: CorrelationId = CorrelationId("42")

  "DownstreamClient" should {
    "call the downstream service and provide the body" in WsTestClient
      .withClient { wsClient =>
        stubFor(
          get(urlEqualTo("/downstream"))
            .withHeader(CorrelationId.CORRELATION_ID_HEADER_NAME, equalTo(SOME_CORRELATION_ID.id))
            .willReturn(
              aResponse
                .withStatus(HttpStatus.SC_OK)
                .withBody(SOME_BODY)
            ))
        val downstreamClient = new DownstreamClient(wsClient)

        Await.result(downstreamClient.callDownstream, 5.seconds) mustEqual SOME_BODY
      }
  }

}
