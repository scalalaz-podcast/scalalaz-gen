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

import java.nio.file.{Files, Path, Paths}

import ru.scalalaz.gen.{Page, PageFile}

import html.*

class SpecialPagesHTMLWriter(targetDir: String) {

  def write(pages: Seq[PageFile]): Unit = {
    val pageUnits = pages.map { f =>
      val fName = f.path.getFileName.toString.replace(".md", ".html")
      SpecialPage(fName, f.page)
    }

    pageUnits.foreach(_.write(targetDir))
  }
}

trait PageUnit {

  val fileName: String

  def content: String

  def write(to: String): Path = {
    val path = Paths.get(to, fileName)
    Files.write(path, content.getBytes())
  }
}

case class SpecialPage(fileName: String, page: Page) extends PageUnit {
  override def content: String = special_page(page).body
}
