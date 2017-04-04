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

import java.nio.file.Path
import java.time.format.DateTimeFormatter
import java.time.{ LocalDate, ZoneOffset }

import knockoff.DefaultDiscounter._
import _root_.knockoff._

/**
  * Фигня с сылкой на запись, кол-вом байт и типом
  */
case class Enclosure(url: String, length: Int, `type`: String = "audio/mpeg")

/**
  * То из чего собирается rss-кусок на каждый выпуск
  */
case class EpisodeSettings(title: String,
                           description: String,
                           audio: Enclosure,
                           page: String,
                           date: LocalDate) {

  def RFCDate: String = {
    val dateTime = date.atStartOfDay().atOffset(ZoneOffset.UTC)
    dateTime.format(DateTimeFormatter.RFC_1123_DATE_TIME)
  }

  def ISODate: String = {
    date.format(DateTimeFormatter.ISO_DATE)
  }
}

case class Episode(settings: EpisodeSettings, content: String) {

  def title: String = settings.title

  def asHtml: String = {
    val blocks = knockoff(content)
    toXHTML(blocks).mkString
  }

}
case class EpisodeFile(path: Path, episode: Episode)
