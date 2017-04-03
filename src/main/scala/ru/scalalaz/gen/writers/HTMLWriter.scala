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

  def write(episodes: Seq[EpisodeFile]): Unit = {
    val episodeUnits = episodes.map(f => {
      val fName = f.path.getFileName.toString.replace(".md", ".html")
      EpisodePage(fName, discusCode, f.episode)
    })

    episodeUnits.foreach(_.write(targetDir))

    MainPage(episodeUnits.reverse).write(targetDir)
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

case class MainPage(episodes: Seq[EpisodePage]) extends PageUnit {

  val fileName: String = "index.html"

  override def content: String =
    main_page("Scalalaz Podcast", episodes).body

}
