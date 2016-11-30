package ru.scalalaz.gen.parsing

import fastparse.all._

case class FileFormat(
  header: Map[String, String],
  otherData: String)

object FormatParser {

  val key     = P(CharIn('a' to 'z') | CharIn('0' to '9') | "." | "_" | "-").rep.!
  val value   = P(CharsWhile(_ != '\n')).!

  val pair = P(key ~ "=" ~ value ~ "\n")
  val head = P("\n".rep.? ~ pair.rep ~ "-".rep(min = 2) ~ "\n")

  val showNotes = P(AnyChar.rep).!

  val format = P(head ~ showNotes).map({
    case (pairs, data) => FileFormat(pairs.toMap, data)
  })

  def parseContent(content: String): Either[ParseError, FileFormat] =
    format.parse(content) match {
      case Parsed.Success(parsed, index) => Right(parsed)
      case f: Parsed.Failure =>
        Left(ParseError(f))
    }
}

