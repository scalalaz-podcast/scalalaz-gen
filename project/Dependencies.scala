import sbt._

object Version {
  final val Scala     = "2.13.10"
  final val ScalaTest = "3.2.14"
  final val ScalaTags = "0.12.0"
  final val FastParse = "2.3.3"
  final val Cats      = "2.9.0"
  final val Knockoff       = "0.9.0"
  final val TypesafeConfig = "1.4.2"
}

object Library {
  val scalaTest      = "org.scalatest"     %% "scalatest" % Version.ScalaTest
  val scalatags      = "com.lihaoyi"       %% "scalatags" % Version.ScalaTags
  val fastParse      = "com.lihaoyi"       %% "fastparse" % Version.FastParse
  val catsCore       = "org.typelevel"     %% "cats-core" % Version.Cats
  val knockoff       = "org.foundweekends" %% "knockoff"  % Version.Knockoff
  val typesafeConfig = "com.typesafe"       % "config"    % Version.TypesafeConfig
}
