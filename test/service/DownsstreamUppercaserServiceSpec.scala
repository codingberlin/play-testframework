package service

import controllers.CorrelationId
import org.scalatestplus.play._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import org.mockito.Matchers.{eq => eqTo, _}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

class DownsstreamUppercaserServiceSpec extends PlaySpec with MockitoSugar {
  val FAILED_FUTURE = Future.failed(new RuntimeException("expected exception"))
  implicit val SOME_CORRELATION_ID: CorrelationId = CorrelationId("42")

  val downstreamClientMock = mock[DownstreamClient]

  "DownstreamUppercaserService" should {
    "should uppercase the provided downstreamClient result" in {
      when(downstreamClientMock.callDownstream(eqTo(SOME_CORRELATION_ID), any[ExecutionContext]))
        .thenReturn(Future.successful("some result"))

      val downstreamUppercaserService = new DownstreamUppercaserService(downstreamClientMock)

      Await.result(downstreamUppercaserService.uppercaseDownstream, 1.second) mustBe "SOME RESULT"
    }

    "should not recover failed downstream" in {
      when(downstreamClientMock.callDownstream(eqTo(SOME_CORRELATION_ID), any[ExecutionContext]))
        .thenReturn(FAILED_FUTURE)

      val downstreamUppercaserService = new DownstreamUppercaserService(downstreamClientMock)

      Await.ready(downstreamUppercaserService.uppercaseDownstream, 1.second) mustBe FAILED_FUTURE
    }
  }

}
