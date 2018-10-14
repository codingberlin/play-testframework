package functional.validators

import functional.framework.RestCallResponseValidator
import play.api.libs.ws.WSResponse
import play.api.test.Helpers._

class NoContentRestCallResponseValidator extends RestCallResponseValidator {

  override def expectedStatusCode: Int = NO_CONTENT

  override def validateSpecific(response: WSResponse): Unit = {
    response.body mustEqual ""
  }
}
