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

import fastparse._
import fastparse.Parsed.TracedFailure
import NoWhitespace._

case class FileFormat(header: Map[String, Option[String]], otherData: String)

object FormatParser {
  def key[_: P]: P[String] = P(CharIn("a-z") | CharIn("0-9") | "." | "_" | "-").rep.!
  def value[_: P]: P[String] = P(CharsWhile(_ != '\n')).!

  def pair[_: P]: P[(String, Option[String])] = P(key ~ "=" ~ value.? ~ "\n")
  def head[_: P]: P[Seq[(String, Option[String])]] = P("\n".rep.? ~ pair.rep ~ "-".rep(2) ~ "\n")

  def showNotes[_: P]: P[String] = P(AnyChar.rep).!

  def format[_: P]: P[FileFormat] = P(head ~ showNotes).map({
    case (pairs, data) =>
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
  })

  def parseContent(content: String): Either[TracedFailure, FileFormat] =
    parse(content, format(_)) match {
      case Parsed.Success(parsed, _) => Right(parsed)
      case f: Parsed.Failure =>
        Left(f.trace())
    }
}
