import sbt._

object Version {
  final val Scala     = "2.13.7"
  final val ScalaTest = "3.2.10"
  final val ScalaTags = "0.11.0"
  final val FastParse = "2.3.3"
  final val Cats      = "2.7.0"
  // both sbt-twirl and knockoff depend on scala-xml 1.x
  // starting version 0.9 knockoff depends on scala-xml 2.x which makes it impossible for us to use
  // for the additional context see https://github.com/playframework/twirl/blob/b2e049d993d1522249e79837ee90ad39f317a80e/build.sbt#L11-L16
  final val Knockoff       = "0.8.14"
  final val TypesafeConfig = "1.4.1"
}

object Library {
  val scalaTest      = "org.scalatest"     %% "scalatest" % Version.ScalaTest
  val scalatags      = "com.lihaoyi"       %% "scalatags" % Version.ScalaTags
  val fastParse      = "com.lihaoyi"       %% "fastparse" % Version.FastParse
  val catsCore       = "org.typelevel"     %% "cats-core" % Version.Cats
  val knockoff       = "org.foundweekends" %% "knockoff"  % Version.Knockoff
  val typesafeConfig = "com.typesafe"       % "config"    % Version.TypesafeConfig
}
