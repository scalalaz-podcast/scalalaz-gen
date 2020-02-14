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

package ru.scalalaz.gen.parsing

import java.time.LocalDate
import java.time.format.{ DateTimeFormatter, DateTimeParseException }

import cats.Apply
import cats.data.Validated.Valid
import cats.data.{ Validated, ValidatedNel }
import ru.scalalaz.gen.{ Enclosure, EpisodeSettings, SpecialPageSettings }

object EpisodeSettingsExtractor {

  import ru.scalalaz.gen.parsing.EpisodeErrors._

  /**
    * Достаем title, enclosure, pageUrl, дату создания
    */
  def fromMap(
      map: Map[String, Option[String]]
  ): ValidatedNel[EpisodeParseError, EpisodeSettings] =
    new SettingsExtractor(map).extract

  class SettingsExtractor(map: Map[String, Option[String]]) {

    def extract: ValidatedNel[EpisodeParseError, EpisodeSettings] =
      Apply[ValidatedNel[EpisodeParseError, *]].map6(
          read("title").toValidatedNel,
          optRead("description").toValidatedNel,
          read("audio.url").toValidatedNel,
          read("audio.length").toValidatedNel,
          read("page").toValidatedNel,
          read("date").andThen(parseDate).toValidatedNel
      ) {
        case (title, desrc, encUrl, encLength, page, date) =>
          val enc =
            Enclosure(encUrl, if (encLength != "") encLength.toInt else -1)
          EpisodeSettings(title, desrc, enc, page, date)
      }

    private def read(key: String): Validated[EpisodeParseError, String] =
      Validated.fromOption(map.get(key).flatten, MissingKey(key))

    private def optRead(key: String): Validated[EpisodeParseError, String] =
      Valid(map.get(key).flatten.getOrElse(""))

    private def parseDate(
        date: String
    ): Validated[EpisodeParseError, LocalDate] = {
      def toDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
      Validated
        .catchOnly[DateTimeParseException](toDate)
        .leftMap(e => InvalidDate(e.getMessage))
    }

  }

}

object PageSettingsExtractor {

  import ru.scalalaz.gen.parsing.SpecialPageErrors._
  
  /**
    * Достаем title, дату создания
    */
  def fromMap(
      map: Map[String, Option[String]]
  ): ValidatedNel[PageParseError, SpecialPageSettings] =
    new SettingsExtractor(map).extract

  class SettingsExtractor(map: Map[String, Option[String]]) {

    def extract: ValidatedNel[PageParseError, SpecialPageSettings] =
      Apply[ValidatedNel[PageParseError, *]].map2(
          read("title").toValidatedNel,
          read("date").andThen(parseDate).toValidatedNel
      ) {
        case (title, date) =>
          //val enc = Enclosure(encUrl, if (encLength != "") encLength.toInt else -1)
          SpecialPageSettings(title, date)
      }

    private def read(key: String): Validated[PageParseError, String] =
      Validated.fromOption(map.get(key).flatten, MissingKey(key))

    private def optRead(key: String): Validated[PageParseError, String] =
      Valid(map.get(key).flatten.getOrElse(""))

    private def parseDate(
        date: String
    ): Validated[PageParseError, LocalDate] = {
      def toDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
      Validated
        .catchOnly[DateTimeParseException](toDate)
        .leftMap(e => InvalidDate(e.getMessage))
    }

  }

}
