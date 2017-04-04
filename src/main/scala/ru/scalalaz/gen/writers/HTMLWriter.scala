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

import java.nio.file._

import ru.scalalaz.gen._

import html._

class HTMLWriter(targetDir: String, discusCode: String) {

  val pageCount = 5

  def write(episodes: Seq[EpisodeFile]): Unit = {
    val episodeUnits = episodes.map(f => {
      val fName = f.path.getFileName.toString.replace(".md", ".html")
      EpisodePage(fName, discusCode, f.episode)
    })

    episodeUnits.foreach(_.write(targetDir))

    writeMain(episodeUnits)
  }

  private def writeMain(episodes: Seq[EpisodePage]): Unit = {
    val sorted =
      episodes.sortWith((e1, e2) => e1.fileName.compareTo(e2.fileName) > 0)

    val splitted = sorted
      .grouped(pageCount)
      .zipWithIndex
      .map({
        case (eps, i) =>
          val file = if (i == 0) "index.html" else s"index-$i.html"
          eps -> file
      })
      .toList

    val paginated = Pagination
      .forList(splitted)
      .map(p => {
        val eps  = p.current._1
        val name = p.current._2
        val prev = p.prev.map(_._2)
        val next = p.next.map(_._2)
        MainPage(name, eps, prev, next)
      })

    paginated.foreach(_.write(targetDir))
  }

}

trait PageUnit {

  val fileName: String

  def content: String

  def write(to: String): Path = {
    val path = Paths.get(to, fileName)
    Files.write(path, content.getBytes())
  }
}

case class EpisodePage(fileName: String, disqusCode: String, episode: Episode)
    extends PageUnit {

  override def content: String =
    episode_page(episode, disqusCode).body

}

case class MainPage(fileName: String,
                    episodes: Seq[EpisodePage],
                    prev: Option[String],
                    next: Option[String])
    extends PageUnit {

  override def content: String =
    main_page("Scalalaz Podcast", episodes, prev, next).body

}
