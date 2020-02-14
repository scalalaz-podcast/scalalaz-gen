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

import cats.data.Validated
import cats.implicits._
import ru.scalalaz.gen.Episode

object EpisodeParser {

  import ru.scalalaz.gen.parsing.EpisodeErrors._

  def fromString(content: String): Validated[EpisodeParseError, Episode] =
    FormatParser
      .parseContent(content)
      .toValidated
      .leftMap(e => {
        InvalidFormat(e.longAggregateMsg)
      })
      .andThen(f => fromFormat(f))

  def fromFormat(format: FileFormat): Validated[EpisodeParseError, Episode] =
    EpisodeSettingsExtractor
      .fromMap(format.header)
      .map(rss => Episode(rss, format.otherData))
      .leftMap(list => ManyErrors(list))

}

