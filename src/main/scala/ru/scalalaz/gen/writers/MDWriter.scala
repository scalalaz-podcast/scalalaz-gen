package ru.scalalaz.gen.writers

import java.nio.file.{Files, Paths}

import laika.api.Transform
import laika.parse.markdown.Markdown
import laika.render.HTML
import Directives._
import ru.scalalaz.gen.EpisodeFile

class MDWriter(tmpDir: String, dir: String) extends Writer {

  private val mdParser =
    Markdown.withBlockDirectives(discussBlock,
      audioControlsBlock,
      rssStartBlock,
      rssEndBlock)

  def write(episodes: Seq[EpisodeFile]): Unit = {
    dumpMarkdown(tmpDir, episodes)
    generateHtml(tmpDir, dir)
  }

  def dumpMarkdown(dir: String, episodes: Seq[EpisodeFile]): Unit =
    episodes.foreach(dumpEpisode(dir, _))

  def dumpEpisode(dir: String, ef: EpisodeFile): Unit = {
    val path = Paths.get(dir, ef.name)
    val data = ef.episode.сontent.getBytes
    Files.write(path, data)
  }

  private def generateHtml(mdDir: String, targetDir: String): Unit =
    Transform from mdParser to HTML fromDirectory mdDir toDirectory targetDir

}
