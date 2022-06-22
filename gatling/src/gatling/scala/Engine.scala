import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import util.IDEPathHelper

object Engine extends App {
  val props = new GatlingPropertiesBuilder()
    .resourcesDirectory(IDEPathHelper.gradleResourcesDirectory.toString)
    .resultsDirectory(IDEPathHelper.resultsDirectory.toString)
    .binariesDirectory(IDEPathHelper.gradleBinariesDirectory.toString)

  Gatling.fromMap(props.build)
}