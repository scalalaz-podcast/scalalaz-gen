ThisBuild / scalaVersion := Version.Scala

lazy val `scalalaz-gen` = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning, SbtTwirl)
  .settings(scalaVersion := Version.Scala)

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.0" cross CrossVersion.full)

libraryDependencies ++= Vector(
  Library.scalaTest % Test,
  Library.scalatags,
  Library.fastParse,
  Library.catsCore,
  Library.knockoff,
  Library.typesafeConfig
)

initialCommands := """|import ru.scalalaz.gen._
                      |""".stripMargin

TwirlKeys.templateImports ++= Seq(
  "ru.scalalaz.gen._",
  "ru.scalalaz.gen.writers._"
)
