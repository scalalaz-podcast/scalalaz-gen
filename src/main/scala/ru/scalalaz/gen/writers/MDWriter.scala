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

    val files: List[File] = getListOfFiles(from, ".md")

    val markdowns: List[MDData] = files.map { file =>
      val s    = Source.fromFile(file)
      val data = MDData(s.getLines().mkString("\n"), file.getName)
      s.close()
      data
    }

    val htmlData: List[HTMLData] = markdowns.map { mdData =>
      val nodes = knockoff(mdData.text)
//      val title = nodes
//        .getOrElse("No title")
//        .find(_.isInstanceOf[Header]).head.span
//              .map(_.toString)
      val html  = toXHTML(nodes).mkString
      val tHtml = default_template("no title", html).body
      HTMLData(tHtml, nodes, mdData.filename.split('.').head + ".html")
    }

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
