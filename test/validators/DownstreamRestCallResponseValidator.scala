package validators

import framework.RestCallResponseValidator
import play.api.libs.ws.WSResponse

import scala.beans.BeanProperty
class DownstreamRestCallResponseValidator extends RestCallResponseValidator {

  @BeanProperty var expectedStatusCode: Int = _
  @BeanProperty var expectedBody: String = _

  override def validateSpecific(response: WSResponse): Unit = {
    Option(expectedBody).foreach { body =>
      response.body mustEqual body
    }
  }
}
