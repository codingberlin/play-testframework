package functional.framework

import java.io.File

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import play.api.libs.ws.WSClient
import play.api.test._
import scala.collection.JavaConverters._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.Source

class FunctionalTestsSpec extends PlaySpec with GuiceOneServerPerSuite with Injecting with WireMockSupport {

  def loadAllTestCasesFromResourceFolder(folderName: String): Iterable[TestCase] = {
    val yaml = new Yaml(new Constructor(classOf[TestCase]))
    val path = getClass.getResource(folderName)
    val allFilesContent = new File(path.getPath).listFiles
      .map(Source.fromFile)
      .map(_.getLines.mkString("\n"))
      .mkString("\n---\n")

    yaml
      .loadAll(allFilesContent)
      .asScala
      .map(_.asInstanceOf[TestCase])
  }

  loadAllTestCasesFromResourceFolder("/testcases")
    .foreach(testCase =>
      testCase.description in {
        testCase.mocks.forEach(_.setupStubs())
        val response = Await.result(testCase.executor.execute(app.injector.instanceOf[WSClient], port), 1.second)
        testCase.mocks.forEach(_.verifyInvocations())
        testCase.validator.validate(response)
    })
}
