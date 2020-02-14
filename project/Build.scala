import com.typesafe.sbt.GitPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin
import org.scalafmt.sbt._
import play.twirl.sbt.SbtTwirl
import sbt._
import sbt.plugins.JvmPlugin
import sbt.Keys._

object Build extends AutoPlugin {

  override def requires = JvmPlugin && GitPlugin&& HeaderPlugin && SbtTwirl

  override def trigger = allRequirements

  override def projectSettings =
    Vector(
      // Core settings
      organization := "ru",
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
      mappings.in(Compile, packageBin) += baseDirectory.in(ThisBuild).value / "LICENSE" -> "LICENSE",
      scalaVersion := Version.Scala,
      crossScalaVersions := Vector(scalaVersion.value),
      scalacOptions ++= Vector(
        "-unchecked",
        "-deprecation",
        "-feature",
        "-language:_",
        "-target:jvm-1.8",
        "-encoding",
        "UTF-8"
      ),
      unmanagedSourceDirectories.in(Compile) := Vector(scalaSource.in(Compile).value),
      unmanagedSourceDirectories.in(Test) := Vector(scalaSource.in(Test).value),

      // Git settings
      GitPlugin.autoImport.git.useGitDescribe := true,

      // Header settings
      HeaderPlugin.autoImport.headerLicense := Some(HeaderPlugin.autoImport.HeaderLicense.ALv2("2016", "Scalalaz Podcast Team"))

    )
}
