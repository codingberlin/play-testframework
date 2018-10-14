package functional.framework

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration._
import org.scalatest.{BeforeAndAfterEach, Suite}

trait WireMockSupport extends BeforeAndAfterEach {
  this: Suite =>

  private val wiremockPort = 9000
  private val wireMockServer = new WireMockServer(wireMockConfig().port(wiremockPort))

  override def beforeEach: Unit = {
    wireMockServer.start()
    WireMock.configureFor("localhost", wiremockPort)
  }

  override def afterEach: Unit = {
    wireMockServer.stop()
  }

  val wiremockUrl: String = s"http://localhost:$wiremockPort"

}
