lazy val `scalalaz-gen` = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

libraryDependencies ++= Vector(
  Library.scalaTest % "test",
  Library.laika,
  Library.scalatags,
  Library.fastParse,
  Library.cats
)

initialCommands := """|import ru.scalalaz.gen._
                      |""".stripMargin

