package ru.scalalaz.gen.parsing

import fastparse.all._

case class FileFormat(
  header: Map[String, String],
  otherData: String)

object FormatParser {

  val key     = P(CharsWhile(_ != '=')).!
  val value   = P(CharsWhile(_ != '\n')).!
  val pair    = P(key ~ "=" ~ value).map({ case (k, v) => k.trim -> v.trim })

  val delimiter = P("\n" ~ P("-".rep(min = 2)) ~ "\n")
  val showNotes = P(AnyChar.rep).!

  val format = P(pair.rep ~ delimiter ~ showNotes).map({
    case (pairs, data) => FileFormat(pairs.toMap, data)
  })

  def parseContent(content: String): Either[ParseError, FileFormat] =
    format.parse(content) match {
      case Parsed.Success(parsed, index) => Right(parsed)
      case f: Parsed.Failure => Left(ParseError(f))
    }
}

