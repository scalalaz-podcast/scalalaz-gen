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

import java.nio.file.{Files, Paths}
import java.util.Collections
import java.util.stream.Collectors

import cats.data.Validated
import cats.implicits._
import ru.scalalaz.gen.{Episode, EpisodeFile}

import scala.collection.JavaConversions._


object EpisodeParser {

  def fromDirectory(dir: String): Seq[Validated[EpisodeParseError, EpisodeFile]] = {
    val files =
      Paths.get(dir).toFile.list()
        .map(Paths.get(_).toFile)
        .filter(f => !f.isDirectory && f.getName.endsWith(".md"))

    files.map(f => {
      val path = Paths.get(dir, f.getPath)
      val data = Files.readAllLines(path).mkString("\n")
      val name = f.getName
      fromString(data)
        .map(e => EpisodeFile(name, e))
        .leftMap(e => FileParseError(name, e))
    })
  }

  def fromString(content: String): Validated[EpisodeParseError, Episode] =
    FormatParser.parseContent(content).toValidated
      .leftMap(e => InvalidFormat(e.failure.msg))
      .andThen(f => fromFormat(f))

  def fromFormat(format: FileFormat): Validated[EpisodeParseError, Episode] =
    RssParser.fromMap(format.header)
      .map(rss => Episode(rss, format.otherData))
      .leftMap(list => ManyErrors(list))

}



