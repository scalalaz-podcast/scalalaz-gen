/*
 * Copyright 2016 Scalalaz Podcast Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.scalalaz.gen.writers

import ru.scalalaz.gen.{Episode, EpisodeFile}

import knockoff.DefaultDiscounter.{knockoff, toXHTML}
import scalatags.Text.TypedTag
import scalatags.Text.all._

import java.nio.file.{Files, Paths}

case class ITunesInfo(title: String,
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
    val url = "https://scalalaz.ru"
    ITunesInfo(title = "Scalalaz Podcast",
               link = url,
               description = "Подкаст о программировании на языке Scala (16+)",
               language = "ru-RU",
               atomLink = s"$url/rss/feed.xml",
               imageLink = s"$url/files/scalalaz.jpg",
               ownerName = "Scalalaz Podcast",
               email = "info@scalalaz.ru",
               category = "Technology")
  }

}

class RSSWriter(dir: String, iTunesInfo: ITunesInfo) {

  def write(episodes: Seq[EpisodeFile]): Unit = {
    val xml  = toXML(episodes.sortBy(_.episode.settings.title))
    val path = Paths.get(dir, "feed.xml")
    Files.write(path, xml.getBytes)
  }

  private def toXML(episodes: Seq[EpisodeFile]): String = {
    import iTunesInfo._
    val head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"

    val ep: Seq[Either[String, TypedTag[String]]] = episodes.map { e =>
      if (e.episode.settings.audio.url != "")
        Right(toItem(e.episode))
      else Left("episode doesnt'has audio")
    }

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
            raw(s"""<itunes:author>$ownerName</itunes:author>"""),
            raw(s"""<itunes:image href="$imageLink" />"""),
            raw(s"""<itunes:owner>
              |  <itunes:name>$ownerName</itunes:name>
              |  <itunes:email>$email</itunes:email>
              |</itunes:owner>
           """.stripMargin),
            raw(s"""<itunes:category text="$category"></itunes:category>"""),
            raw(s"""<itunes:explicit>no</itunes:explicit>"""),
            tag("managingEditor")(s"$email ($ownerName)"),
            ep.filter(_.isRight).map(_.getOrElse(throw new Exception(s"toXML call failed for the following files: ${episodes.map(_.path)}")))
        )
    )
    head + xml.toString()
  }

  private def toItem(e: Episode): TypedTag[String] = {
    import e.settings._

    tag("item")(tag("title")(title),
                raw(s"""<description>
           |<![CDATA[<pre>
           |${ toXHTML(knockoff(e.content)).mkString }
           |</pre>]]>
           |</description>""".stripMargin),
                tag("enclosure")(attr("url") := audio.url,
                                 attr("type") := audio.`type`,
                                 attr("length") := audio.length),
                tag("guid")(attr("isPermaLink") := "false", page),
                tag("pubDate")(RFCDate),
                tag("link")(page))

  }
}
