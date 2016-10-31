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

package ru.scalalaz.gen

import java.time.LocalDateTime

import fastparse.all._

object EpisodeParser {

  val key     = P(CharsWhile(_ != '=')).!
  val value   = P(CharsWhile(_ != '\n')).!
  val pair    = P(key ~ "=" ~ value).map({ case (k, v) => k -> v })

  val delimiter = P("\n" ~ P("-".rep(min = 2)) ~ "\n")
  val showNotes = P(AnyChar.rep).!

  val header = P(pair.rep ~ delimiter ~ showNotes)

  def fromString(str: String): Either[Episode, ParseError] = {
    header.parse(str) match {
      case Parsed.Success((pairs, content), index) =>
        val rssItem = mapToRssItem(pairs.toMap)
        Left(Episode(rssItem, content))
      case f: Parsed.Failure => Right(ParseError(f))
    }
  }

  private def mapToRssItem(items: Map[String, String]): RssItem = {
    def read(key: String): Option[String] = items.get(key)
    for {

    }
    RssItem("", "", Enclosure("", "", 0), "", LocalDateTime.now())
  }

  def fromDirectory(directory: String): Unit = {}

}


