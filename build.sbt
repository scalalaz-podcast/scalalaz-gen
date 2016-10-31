lazy val `scalalaz-gen` = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

libraryDependencies ++= Vector(
  Library.scalaTest % "test",
  Library.laika,
  Library.scalatags,
  Library.fastParse,
  Library.cats
)

initialCommands := """|import ru.scalalaz.gen._
                      |""".stripMargin

