package ru.scalalaz.gen.parsing

import org.scalatest.{FlatSpec, Inside, Matchers}

class FormatParserSpec extends FlatSpec with Matchers with Inside {

  val raw =
    """
      |title=value
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

}
