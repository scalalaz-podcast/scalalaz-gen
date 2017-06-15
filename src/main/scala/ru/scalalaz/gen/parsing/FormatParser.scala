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

import fastparse.all._

case class FileFormat(header: Map[String, Option[String]], otherData: String)

object FormatParser {

  val key   = P(CharIn('a' to 'z') | CharIn('0' to '9') | "." | "_" | "-").rep.!
  val value = P(CharsWhile(_ != '\n')).!

  val pair = P(key ~ "=" ~ value.? ~ "\n")
  val head = P("\n".rep.? ~ pair.rep ~ "-".rep(min = 2) ~ "\n")

  val showNotes = P(AnyChar.rep).!

  val format = P(head ~ showNotes).map({
    case (pairs, data) => {
      FileFormat(pairs
                   .map(
                       x =>
                         (x._1, x._2 match {
                       case Some(v) => Some(v)
                       case None    => Some("")
                     })
                   )
                   .toMap,
                 data)
    }
  })

  def parseContent(content: String): Either[ParseError, FileFormat] =
    format.parse(content) match {
      case Parsed.Success(parsed, index) => Right(parsed)
      case f: Parsed.Failure =>
        Left(ParseError(f))
    }
}
