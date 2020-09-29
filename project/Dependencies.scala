import sbt._

object Version {
  final val Scala          = "2.13.3"
  final val ScalaTest      = "3.2.2"
  final val ScalaTags      = "0.9.1"
  final val FastParse      = "2.3.0"
  final val Cats           = "2.2.0"
  final val Knockoff       = "0.8.14"
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
