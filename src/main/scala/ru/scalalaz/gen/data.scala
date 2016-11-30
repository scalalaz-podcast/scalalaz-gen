package ru.scalalaz.gen

import java.nio.file.Path
import java.time.LocalDate


/**
  * Фигня с сылкой на запись, кол-вом байт и типом
  */
case class Enclosure(
  url: String,
  length: Int,
  `type`: String = "audio/mpeg")


/**
  * То из чего собирается rss-кусок на каждый выпуск
  */
case class RssItem(
  title: String,
  description: String,
  enclosure: Enclosure,
  page: String,
  date: LocalDate)


case class Episode(rss: RssItem, сontent: String)
case class EpisodeFile(path: Path, episode: Episode)
