package ru.scalalaz.gen.parsing

import org.scalatest.{FlatSpec, Inside, Matchers}

class FormatParserSpec extends FlatSpec with Matchers with Inside {

  val raw =
    """title=value
      |key2=value2
      |----
      |### Yoyoyo!
      |it is a new episode!""".stripMargin

  it should "parse from string" in {
    val result = FormatParser.parseContent(raw)
    inside(result) {
      case Right(parsed) =>
        parsed.header shouldBe Map("title" -> "value", "key2" -> "value2")
        parsed.otherData shouldBe "### Yoyoyo!\nit is a new episode!"
    }
  }

  val raw2 =
    """
|title=Выпуск 01
|enc.url=http://scalalaz.ru/mp3/scalalaz-podcast-1.mp3
|enc.length=63337733
|page=http://scalalaz.ru/series-01.html
|date=2016-08-07
|----
|### Выпуск 01
|
|@:audioControls "http://scalalaz.ru/mp3/scalalaz-podcast-1.mp3".
|
|Темы:
|
|- [Релиз-кандидат Akka](http://akka.io/news/2016/08/02/akka-…4.9-RC1-released.html)
|- [Релиз Spark](https://exit.sc/?urlhttp%3A%2F%2Fspark.apache.org%2Freleases%2Fspark-release-2-0-0.html)
|- [Релиз Spark](https://exit.sc/?urlhttp%3A%2F%2Fspark.apache.org%2Freleases%2Fspark-release-2-0-0.html)
|- [Релиз Spark](https://exit.sc/?url=http%3A%2F%2Fspark.apache.org%2Freleases%2Fspark-release-2-0-0.html)
|- [Pants](http://www.pantsbuild.org/)""".stripMargin

  it should "parse sadasd" in {
    val result = FormatParser.parseContent(raw2)
    inside(result) {
      case Right(parsed) => println(parsed)
    }
  }
}
