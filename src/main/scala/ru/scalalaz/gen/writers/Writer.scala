package ru.scalalaz.gen.writers

import ru.scalalaz.gen.EpisodeFile

trait Writer {

  def write(episodes: Seq[EpisodeFile]): Unit
}
