lazy val `scalalaz-gen` = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

libraryDependencies ++= Vector(
  Library.scalaTest % "test",
  Library.laika
)

initialCommands := """|import ru.scalalaz.gen._
                      |""".stripMargin

