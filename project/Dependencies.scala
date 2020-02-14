import sbt._

object Version {
  final val Scala          = "2.12.10"
  final val ScalaTest      = "3.1.0"
  final val ScalaTags      = "0.8.5"
  final val FastParse      = "2.2.4"
  final val Cats           = "2.1.0"
  final val Knockoff       = "0.8.12"
  final val TypesafeConfig = "1.4.0"
}

object Library {
  val scalaTest      = "org.scalatest"     %% "scalatest" % Version.ScalaTest
  val scalatags      = "com.lihaoyi"       %% "scalatags" % Version.ScalaTags
  val fastParse      = "com.lihaoyi"       %% "fastparse" % Version.FastParse
  val catsCore       = "org.typelevel"     %% "cats-core" % Version.Cats
  val knockoff       = "org.foundweekends" %% "knockoff"  % Version.Knockoff
  val typesafeConfig = "com.typesafe"       % "config"    % Version.TypesafeConfig
}
