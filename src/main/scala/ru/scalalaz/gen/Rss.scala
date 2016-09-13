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

import java.io.{ BufferedWriter, File, FileWriter }

import scalatags.Text.TypedTag
import scalatags.Text.all._

trait Rss {
  import fastparse.all._

  case class RssTag(name: String, content: String)
  case class RssTags(tags: Seq[RssTag])

  // parsers
  val any          = P(AnyChar.rep)
  val ws           = P(" ".rep)
  val value        = P(CharIn('a' to 'z', "_", "-").rep(1).!)
  val tagParameter = P("tag=")
  val start        = P("@:rssTagStart" ~ ws ~ tagParameter ~ value ~ ":")
  val end          = P("@:rssTagEnd" ~ ws ~ tagParameter ~ value ~ ".")
  val content =
    P(start ~ (!end ~ AnyChar).rep(1).! ~ end).filter(v => v._1 == v._3)
  val rssTag   = content.map(v => RssTag(v._1, v._2))
  val markdown = ((!rssTag ~ AnyChar).rep ~ rssTag.!).rep

  def parseRssTags(lines: String): Option[TypedTag[String]] = {

    val parsed = markdown.parse(lines)

    val r = parsed match {
      case Parsed.Success(tagSeq, _) =>
        val rssTags: Seq[RssTag] = tagSeq.map { t =>
          val p                      = rssTag.parse(t)
          val Parsed.Success(tag, _) = p
          tag
        }

        if (rssTags.exists(_.name.length > 0))
          Option(writeRssTag(rssTags))
        else
          None

      case f: Parsed.Failure =>
        println(s"Parsed.Failure: ${ f.extra.traced.trace }")
        None
    }
    r
  }

  def writeRssTag(tags: Seq[RssTag]): TypedTag[String] = {
    val xmlItem = tag("item") {
      tags.map { t =>
        tag(t.name)(t.content)
      }
    }
    xmlItem
  }

  def getListOfMdFiles(dir: String): List[File] = {
    val files: List[File] = new java.io.File(dir).listFiles.toList
    files.filter(_.getName.endsWith(".md"))
  }

}

object Rss extends Rss {

  def apply(inPath: String, outPath: String) = {
    def parseFiles(files: List[File]): List[Option[TypedTag[String]]] = {
      for (f <- files) yield {
        val source = scala.io.Source.fromFile(f.getAbsolutePath)
        val lines = try {
          Option(source.mkString)
        } finally {
          source.close()
          None
        }

        lines.flatMap {
          parseRssTags
        }
      }
    }

    val files: List[File] = getListOfMdFiles(inPath)

    val parsed    = parseFiles(files)
    val xmlResult = parsed.filter(_.isDefined).map(_.get)

    val xml = BuildRssXml(xmlResult)

    val file = new File(s"$outPath/rss.xml")
    val bw   = new BufferedWriter(new FileWriter(file))
    bw.write(xml.toString())
    bw.close()

  }

  def BuildRssXml(xmlResult: List[TypedTag[String]]): TypedTag[String] = {
    tag("channel") {
      xmlResult
    }
  }
}
