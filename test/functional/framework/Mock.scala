package functional.framework

trait Mock {
  def setupStubs(): Unit

  def verifyInvocations(): Unit
}
