package functional.framework

import org.scalatest.MustMatchers
import play.api.libs.ws.WSResponse

trait RestCallResponseValidator extends MustMatchers {

  def expectedStatusCode: Int
  def validateSpecific(response: WSResponse): Unit

  def validate(response: WSResponse): Unit = {
    response.status mustBe expectedStatusCode
    validateSpecific(response)
  }
}
