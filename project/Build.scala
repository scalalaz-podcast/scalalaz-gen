import com.github.sbt.git.GitPlugin
import de.heikoseeberger.sbtheader._
import org.scalafmt.sbt._
import play.twirl.sbt.SbtTwirl
import sbt._
import sbt.plugins.JvmPlugin
import sbt.Keys._
import java.time.Year

object Build extends AutoPlugin {

  override def requires = JvmPlugin && GitPlugin && HeaderPlugin && SbtTwirl

  override def trigger = allRequirements

  override def projectSettings =
    Vector(
      // Core settings
      organization := "ru",
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
      Compile / packageBin / mappings += (ThisBuild / baseDirectory).value / "LICENSE" -> "LICENSE",
      scalaVersion                                                                     := Version.Scala,
      crossScalaVersions                                                               := Vector(scalaVersion.value),
      scalacOptions ++= Vector(
        "-unchecked",
        "-deprecation",
        "-feature",
        "-language:_",
        "-Xsource:3",
        "-target:jvm-1.8",
        "-encoding",
        "UTF-8"
      ),
      Compile / unmanagedSourceDirectories := Vector((Compile / scalaSource).value),
      Test / unmanagedSourceDirectories    := Vector((Test / scalaSource).value),

      // Git settings
      GitPlugin.autoImport.git.useGitDescribe := true,

      // Header settings
      HeaderPlugin.autoImport.headerLicense := Some(HeaderPlugin.autoImport.HeaderLicense.ALv2(Year.now.getValue.toString, "Scalalaz Podcast Team")),
      HeaderPlugin.autoImport.headerMappings := Map(
        FileType.scala -> CommentStyle.cStyleBlockComment.copy(
          commentCreator = { (text, existingText) =>
            // preserve year of old headers
            val newText = CommentStyle.cStyleBlockComment.commentCreator.apply(text, existingText)
            existingText.flatMap(_ => existingText.map(_.trim)).getOrElse(newText)
          }
        )
      )
    )
}
