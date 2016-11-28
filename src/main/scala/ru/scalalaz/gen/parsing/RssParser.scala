package ru.scalalaz.gen.parsing

import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeParseException}

import cats.Apply
import cats.data.Validated.Valid
import cats.data.{Validated, ValidatedNel}
import ru.scalalaz.gen.{Enclosure, RssItem}

object RssParser {

  /**
    * Достаем title, enclosure, pageUrl, дату создания
    */
  def fromMap(map: Map[String, String]): ValidatedNel[EpisodeParseError, RssItem] =
    new RssItemExtractor(map).extract

  class RssItemExtractor(map: Map[String, String]) {

    def extract: ValidatedNel[EpisodeParseError, RssItem] =
      Apply[ValidatedNel[EpisodeParseError, ?]].map6(
        read("title").toValidatedNel,
        optRead("description").toValidatedNel,
        read("enc.url").toValidatedNel,
        read("enc.length").toValidatedNel,
        read("page").toValidatedNel,
        read("date").andThen(parseDate).toValidatedNel
      ) {
        case (title, desrc, encUrl, encLength, page, date) =>
          val enc = Enclosure(encUrl, encLength.toInt)
          RssItem(title, desrc, enc, page, date)
      }

    private def read(key: String): Validated[EpisodeParseError, String] =
      Validated.fromOption(map.get(key), MissingKey(key))

    private def optRead(key: String): Validated[EpisodeParseError, String] =
      Valid(map.getOrElse(key, ""))

    private def parseDate(date: String): Validated[EpisodeParseError, LocalDate] = {
      def toDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
      Validated.catchOnly[DateTimeParseException](toDate)
        .leftMap(e => InvalidDate(e.getMessage))
    }

  }

}
