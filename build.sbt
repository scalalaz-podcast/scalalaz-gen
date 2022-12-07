ThisBuild / scalaVersion := Version.Scala

lazy val `scalalaz-gen` = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning, SbtTwirl)
  .settings(scalaVersion := Version.Scala)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.2" cross CrossVersion.full)

libraryDependencies ++= Vector(
  Library.scalaTest % Test,
  Library.scalatags,
  Library.fastParse,
  Library.catsCore,
  Library.knockoff,
  Library.typesafeConfig
)

/**
  * [error] (update) found version conflict(s) in library dependencies; some are suspected to be binary incompatible:
  * [error]
  * [error] 	* com.lihaoyi:geny_2.13:1.0.0 (early-semver) is selected over 0.6.10
  * [error] 	    +- com.lihaoyi:scalatags_2.13:0.12.0                  (depends on 1.0.0)
  * [error] 	    +- com.lihaoyi:fastparse_2.13:2.3.3                   (depends on 0.6.10)
  */
libraryDependencySchemes += "com.lihaoyi" %% "geny" % VersionScheme.Always

initialCommands := """|import ru.scalalaz.gen.*
                      |""".stripMargin

TwirlKeys.templateImports ++= Seq(
  "ru.scalalaz.gen.*",
  "ru.scalalaz.gen.writers.*"
)
