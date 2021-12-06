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

import java.nio.file.Path

import cats.data.NonEmptyList

object EpisodeErrors {

  sealed trait EpisodeParseError

  case class MissingKey(name: String) extends EpisodeParseError

  case class InvalidDate(description: String) extends EpisodeParseError

  case class InvalidFormat(explanation: String) extends EpisodeParseError

  case class ManyErrors(list: NonEmptyList[EpisodeParseError]) extends EpisodeParseError

  case class FileParseError(path: Path, manyErrors: EpisodeParseError) extends EpisodeParseError
}

object SpecialPageErrors {

  sealed trait PageParseError

  case class MissingKey(name: String) extends PageParseError

  case class InvalidDate(description: String) extends PageParseError

  case class InvalidFormat(explanation: String) extends PageParseError

  case class FileParseError(path: Path, manyErrors: PageParseError) extends PageParseError

  case class ManyErrors(list: NonEmptyList[PageParseError]) extends PageParseError

}
