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

import java.nio.file.{ Files, Paths }
import java.io.File

import laika.api.Transform
import laika.parse.markdown.Markdown
import laika.render.{ HTML, PrettyPrint }
import ru.scalalaz.gen.Directives._

import scala.io.Codec

object Main extends App {

  implicit val codec: Codec = Codec.UTF8

  val markdownDir   = getClass.getResource("/md").getPath
  val targetPath    = Paths.get("target/site")
  val targetRssPath = Paths.get("target/site/rss")

  // output directory creation
  if (!Files.exists(targetPath)) {
    Files.createDirectory(targetPath)
  }

  if (!Files.exists(targetRssPath)) {
    Files.createDirectory(targetRssPath)
  }

  // for debugging //
  val testFile = getClass.getResource("/md/03.md").getPath
  println(
      Transform from Markdown.withBlockDirectives(discussBlock,
                                                  audioControlsBlock,
                                                  rssStartBlock,
                                                  rssEndBlock) to
        PrettyPrint fromFile testFile toString
  )

  // html generation
  Transform from Markdown.withBlockDirectives(discussBlock,
                                              audioControlsBlock,
                                              rssStartBlock,
                                              rssEndBlock) to
    HTML fromDirectory markdownDir toDirectory targetPath.toString

  // rss
  Rss(markdownDir, targetRssPath.toString)
}
