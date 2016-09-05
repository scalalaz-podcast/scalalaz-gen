/*
 * Copyright 2016 evgeniy
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

import java.nio.file.{ Files, Paths }

import laika.api.Transform
import laika.directive.Directives
import laika.directive.Directives._
import laika.parse.markdown.Markdown
import laika.render.{ HTML, HTMLWriter, PrettyPrint }
import laika.tree.TreeUtil
import laika.tree.Documents._
import laika.tree.Templates.{ TemplateElement, TemplateString }
import laika.util.Builders._
import laika.tree.Elements._

import scala.collection.JavaConversions._
import scala.io.Codec

/**
  * Created by Evgeniy Tokarev on 03/09/16.
  */
object Main extends App {

  implicit val codec: Codec = Codec.UTF8

  val audioControls: Spans.Directive = Spans.create("audioControls") {
    import Spans.Combinators._
    import Spans.Converters._

    (attribute(Default) ~ body(Default).optional) { (src, content) =>
      RawContent(Seq("html"), s"""|<audio controls="">
            |<source src="$src" type="audio/mpeg">
            |</audio>""".stripMargin)
    }
  }

  val resourcesDir = getClass.getResource("/").getPath
  val targetPath   = Paths.get("target/site")

  // output directory creation
  if (!Files.exists(targetPath)) {
    Files.createDirectory(targetPath)
  }

  // for debugging //
  val testFile = getClass.getResource("/02.md").getPath()
  print(
      Transform from Markdown
        .withSpanDirectives(audioControls) to PrettyPrint fromFile testFile toString
  )
  //

  // html generation
  Transform from Markdown
    .withSpanDirectives(audioControls) to HTML fromDirectory resourcesDir toDirectory targetPath.toString
}
