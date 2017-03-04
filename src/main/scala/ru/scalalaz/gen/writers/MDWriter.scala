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

package ru.scalalaz.gen.writers

import java.io.{ BufferedWriter, File, FileWriter }

import knockoff.DefaultDiscounter._
import _root_.knockoff._

import scala.io.Source
import html.default_template

import scala.language.postfixOps
import scalatags.Text.TypedTag
import scalatags.Text.all._

case class MDWriter(from: String, to: String) {

  case class MDData(text: String, filename: String)
  case class HTMLData(html: String, ast: Seq[Block], filename: String)

  def write(): Unit = {

    def getListOfFiles(directory: String, extension: String): List[File] = {
      val d = new File(directory)
      if (d.exists && d.isDirectory) {
        d.listFiles
          .filter(_.isFile)
          .filter(_.getName.endsWith(extension))
          .toList
      } else {
        List[File]()
      }
    }

    def getHeader(nodes: Seq[Block]): Option[String] = {
      for {
        headerSpans <- nodes.find(_.isInstanceOf[Header]).map {
                        case h: Header => h.spans
                      }
        text <- headerSpans.find(_.isInstanceOf[Text]).map {
                 case t: Text => t.content
               }
      } yield text
    }

    def getDescriptionSpans(nodes: Seq[Block]): Option[Seq[Span]] = {
      val descItems = nodes.find(_.isInstanceOf[OrderedList]).map {
        case l: OrderedList => l.items
      }

      val descSpans = descItems.map {
        case d if d.isEmpty => Seq[Span]()
        case d =>
          d.flatMap { i =>
            i.children.filter(_.isInstanceOf[Paragraph]).flatMap {
              case p: Paragraph => p.spans
            }
          }
      }

      descSpans
    }

    def getPosts(htmlData: List[HTMLData]): Seq[TypedTag[String]] = {
      htmlData.reverse.map { hData =>
        val nodes     = hData.ast
        val header    = getHeader(nodes)
        val descSpans = getDescriptionSpans(nodes)

        val descParagraphs = descSpans.map {
          _.map {
            case s: Text =>
              p(s.content)
          }
        }

        div(a(href := hData.filename)(h2(header)), descParagraphs)
      }
    }

    def generateListOfPosts(htmlData: List[HTMLData]): Unit = {
      val tHtml = default_template(
          "no title",
          ul(`class` := "post-list")(getPosts(htmlData)).toString
      ).body

      val file = new File(s"${ to }/${ "toc.html" }")
      val bw   = new BufferedWriter(new FileWriter(file))
      bw.write(tHtml)
      bw.close()

    }

    val files: List[File] = getListOfFiles(from, ".md")

    val markdowns: List[MDData] = files.map { file =>
      val s    = Source.fromFile(file)
      val data = MDData(s.getLines().mkString("\n"), file.getName)
      s.close()
      data
    }

    val htmlData: List[HTMLData] = markdowns.map { mdData =>
      val nodes = knockoff(mdData.text)
      val html  = toXHTML(nodes).mkString
      val tHtml = default_template("no title", html).body
      HTMLData(tHtml, nodes, mdData.filename.split('.').head + ".html")
    }

    generateListOfPosts(htmlData)

    htmlData.foreach { data =>
      val file = new File(s"${ to }/${ data.filename }")
      val bw   = new BufferedWriter(new FileWriter(file))
      bw.write(data.html)
      bw.close()
    }

    htmlData.foreach(v => println(v.filename))

//    val t = default_template("Hello World!!!")
//    println(t.body)

  }
}
