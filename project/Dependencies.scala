import sbt._

object Version {
  final val Scala     = "2.11.8"
  final val ScalaTest = "3.0.0"
  final val ScalaTags = "0.6.0"
  final val laika = "0.6.0"
}

object Library {
  val scalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest
  val scalatags = "com.lihaoyi" %% "scalatags" % Version.ScalaTags
  val laika = "org.planet42" %% "laika-core" % Version.laika
}
