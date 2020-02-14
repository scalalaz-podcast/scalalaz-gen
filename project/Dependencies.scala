import sbt._

object Version {
  final val Scala     = "2.12.9"
  final val ScalaTest = "3.0.8"
  final val ScalaTags = "0.7.0"
  final val FastParse = "2.2.4"
  final val Cats      = "1.6.1"
  final val Knockoff  = "0.8.12"
  final val TypesafeConfig = "1.3.4"
}

object Library {
  val scalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest
  val scalatags = "com.lihaoyi" %% "scalatags" % Version.ScalaTags
  val fastParse = "com.lihaoyi" %% "fastparse" % Version.FastParse
  val catsCore = "org.typelevel" %% "cats-core" % Version.Cats
  val knockoff = "org.foundweekends" %% "knockoff" % Version.Knockoff
  val typesafeConfig = "com.typesafe" % "config" % Version.TypesafeConfig
}
