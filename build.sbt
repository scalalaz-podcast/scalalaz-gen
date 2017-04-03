lazy val `scalalaz-gen` = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning, SbtTwirl)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

libraryDependencies ++= Vector(
  Library.scalaTest % "test",
  Library.scalatags,
  Library.fastParse,
  Library.cats,
  Library.knockoff,
  Library.typesafeConfig
)

initialCommands := """|import ru.scalalaz.gen._
                      |""".stripMargin

TwirlKeys.templateImports ++= Seq(
  "ru.scalalaz.gen._",
  "ru.scalalaz.gen.writers._"
)
