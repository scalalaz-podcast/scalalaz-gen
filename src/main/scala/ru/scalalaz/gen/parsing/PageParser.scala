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


import cats.implicits._
import cats.data.Validated
import ru.scalalaz.gen.Page

object PageParser {

  import ru.scalalaz.gen.parsing.SpecialPageErrors._

  def fromString(content: String): Validated[PageParseError, Page] =
    FormatParser
      .parseContent(content)
      .toValidated
      .leftMap(e => {
        InvalidFormat(e.longAggregateMsg)
      })
      .andThen(f => fromFormat(f))

  def fromFormat(format: FileFormat): Validated[PageParseError, Page] =
    PageSettingsExtractor
      .fromMap(format.header)
      .map(settings => Page(settings, format.otherData))
      .leftMap(list => ManyErrors(list))

}
