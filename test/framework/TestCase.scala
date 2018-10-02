package framework

import scala.beans.BeanProperty

class TestCase {
  @BeanProperty var description: String = _
  @BeanProperty var mocks: java.util.ArrayList[Mock] = new java.util.ArrayList[Mock]
  @BeanProperty var executor: RestCallExecutor = _
  @BeanProperty var validator: RestCallResponseValidator = _

  override def toString = s"TestCase($description)"
}
