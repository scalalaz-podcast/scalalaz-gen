import sbt._

object Version {
  final val Scala     = "2.11.8"
  final val ScalaTest = "3.0.0"
  final val ScalaTags = "0.6.0"
  final val FastParse = "0.4.1"
  final val Cats = "0.8.0"
  final val Knockoff = "0.8.6"
  final val TypesafeConfig = "1.3.1"
}

object Library {
  val scalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest
  val scalatags = "com.lihaoyi" %% "scalatags" % Version.ScalaTags
  val fastParse = "com.lihaoyi" %% "fastparse" % Version.FastParse
  val cats = "org.typelevel" %% "cats" % Version.Cats
  val knockoff = "org.foundweekends" %% "knockoff" % Version.Knockoff
  val typesafeConfig = "com.typesafe" % "config" % Version.TypesafeConfig
}
