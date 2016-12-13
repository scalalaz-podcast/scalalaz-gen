package ru.scalalaz.gen.parsing

import java.time.LocalDate

import cats.data.Validated.Valid
import org.scalatest.{FlatSpec, Inside, Matchers}

class EpisodeParserSpec extends FlatSpec with Matchers with Inside {

  val episodeStr =
    """
      |title=Episode#1
      |page=http://scalalaz.ru/series-01.html
      |date=2016-11-28
      |enc.url=http://scalalaz.ru/mp3/scalalaz-podcast-1.mp3
      |enc.length=6
      |----
      |### Yoyoyo!
      |it is a new episode!""".stripMargin

  it should "parse from string" in {
    val result = EpisodeParser.fromString(episodeStr)
    inside(result) {
      case Valid(episode) =>
        episode.сontent shouldBe "### Yoyoyo!\nit is a new episode!"

        val rss = episode.rss
        rss.title shouldBe "Episode#1"
        rss.page shouldBe "http://scalalaz.ru/series-01.html"

        rss.date shouldBe LocalDate.of(2016, 11, 28)

        rss.enclosure.url shouldBe "http://scalalaz.ru/mp3/scalalaz-podcast-1.mp3"
        rss.enclosure.length shouldBe 6
    }
  }
}
