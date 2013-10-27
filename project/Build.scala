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
	"postgresql" % "postgresql" % "8.4-702.jdbc4", 
     "mettle" % "mettle_2.10" % "1.0-SNAPSHOT"
     
    )

 	val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Change this to point to your local play repository
      resolvers += Resolver.url("Mettle Repository", url("http://ianrae.github.io/snapshot/"))(Resolver.ivyStylePatterns),
      checksums := Nil
    )

}
            
