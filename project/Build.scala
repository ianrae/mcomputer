import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "mcomputer"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      javaCore,
      javaJdbc,
      javaEbean,
    "commons-io" % "commons-io" % "2.3",
"postgresql" % "postgresql" % "8.4-702.jdbc4"      
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here      
    )

}
            
