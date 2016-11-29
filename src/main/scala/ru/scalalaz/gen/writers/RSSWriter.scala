package ru.scalalaz.gen.writers

import java.nio.file.{Files, Paths}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scalatags.Text.TypedTag
import scalatags.Text.all._
import ru.scalalaz.gen.{Episode, EpisodeFile}

case class ITunesInfo(
  title: String,
  link: String,
  description: String,
  language: String,
  atomLink: String,
  imageLink: String,
  ownerName: String,
  email: String,
  category: String)

object ITunesInfo {

  val Scalalaz = {
    val url = "http://scalalaz.ru"
    ITunesInfo(
      title = "Scalalaz Podcast",
      link = url,
      description = "Подкаст о программировании на языке Scala (16+)",
      language = "ru-RU",
      atomLink = s"$url/rss/feed.xml",
      imageLink = s"$url/files/scalalaz.jpg",
      ownerName = "Scalalaz Podcast",
      email = "info@scalalaz.ru",
      category = "Technology"
    )
  }

}

class RSSWriter(dir: String, iTunesInfo: ITunesInfo) extends Writer {

  override def write(episodes: Seq[EpisodeFile]): Unit = {
    val xml = toXML(episodes)
    val path = Paths.get(dir, "feed.xml")
    Files.write(path, xml.getBytes)
  }

  private def toXML(episodes: Seq[EpisodeFile]): String = {
    import iTunesInfo._
    val head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"

    val xml = tag("rss")(
      attr("version") := "2.0",
      attr("xmlns:itunes") := "http://www.itunes.com/dtds/podcast-1.0.dtd",
      attr("xmlns:atom") := "http://www.w3.org/2005/Atom"
    )(
      tag("channel")(
        tag("title")(title),
        tag("link")(link),
        tag("description")(description),
        tag("language")(language),
        raw(
          s"""<atom:link href="$atomLink" rel="self" type="application/rss+xml" />"""
        ),
        raw(s"""<itunes:image href="$imageLink" />""" ),
        raw(
          s"""<itunes:owner>
              |  <itunes:name>$ownerName</itunes:name>
              |  <itunes:email>$email</itunes:email>
              |</itunes:owner>
           """.stripMargin
        ),
        raw(s"""<itunes:category text="$category" />"""),
        raw(s"""<itunes:explicit>no</itunes:explicit>"""),
        tag("managingEditor")(s"$email $ownerName"),
        episodes.map(e => toItem(e.episode))
      )
    )
    head + xml.toString()
  }

  private def toItem(e: Episode): TypedTag[String] = {
    import e.rss._
    def formattedDate: String = date.format(DateTimeFormatter.RFC_1123_DATE_TIME)
    tag("item")(
      tag("title")(title),
      raw(
        s"""<description>
           |<![CDATA[${e.сontent}]]>
           |</desctiption>""".stripMargin),
      tag("enclosure")(
        attr("url") := enclosure.url,
        attr("type") := enclosure.`type`,
        attr("length") := enclosure.length
      ),
      tag("guid")(
        attr("isPermalink") := "false",
        page
      ),
      tag("pubDate")(formattedDate)
    )
  }
}
