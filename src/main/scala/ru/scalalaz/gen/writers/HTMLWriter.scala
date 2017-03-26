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

import java.nio.file.{ Files, Paths }

import ru.scalalaz.gen.EpisodeFile

case class HtmlPage(fileName: String, data: String)

class HTMLWriter(targetDir: String, discusCode: String) {

  def write(episodes: Seq[EpisodeFile]): Unit = {
    episodes.foreach(episodePage)

  }

  def episodePage(episodeFile: EpisodeFile): Unit = {
    val data = html.episode_template(episodeFile.episode, discusCode).body
    val fileName =
      episodeFile.path.getFileName.toString.replace(".md", ".html")
    val path = Paths.get(targetDir, fileName)
    Files.write(path, data.getBytes)
  }
}
