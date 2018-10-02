package controllers

import scala.concurrent.duration._
import play.api.test._

import scala.concurrent.Await
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.ws.WSClient
import play.api.test.Helpers._


class PingControllerSpec extends PlaySpec with GuiceOneServerPerSuite with Injecting {

    "pong" in {
      val wsClient = app.injector.instanceOf[WSClient]
      val response = Await.result(wsClient.url(s"http://localhost:$port/ping").get(), 1.second)

      response.status mustBe NO_CONTENT
      response.body mustBe ""
    }
}
