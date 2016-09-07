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

import com.typesafe.config.ConfigFactory
import java.nio.file.{ Files, Paths }
import laika.api.Transform
import laika.directive.Directives._
import laika.parse.markdown.Markdown
import laika.render.{ HTML, PrettyPrint }
import laika.tree.Elements._
import laika.util.Builders._
import scala.io.Codec

object Main extends App {

  implicit val codec: Codec = Codec.UTF8

  val config     = ConfigFactory.load()
  val disqusCode = config.getString("disqus.disqusCode")

  val audioControls: Spans.Directive = Spans.create("audioControls") {
    import Spans.Combinators._
    import Spans.Converters._

    (attribute(Default) ~ attribute("type").optional ~ body(Default).optional) {
      (src, `type`, content) =>
        val t = `type`.getOrElse("mpeg")
        RawContent(Seq("html"), s"""|<audio controls="">
            |<source src="$src" type="audio/$t">
            |</audio>""".stripMargin)
    }
  }

  val discuss: Spans.Directive = Spans.create("disqus") {
    import Spans.Combinators._
    import Spans.Converters._

    (attribute(Default).optional ~ body(Default).optional) {
      (value, content) =>
        val disqusJSCode = """
        var disqus_shortname = '""" + disqusCode + """';
        (function() {
            var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
            dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
            (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
        })();"""

        val html =
          s"""<div id="disqus_thread"></div><script type="text/javascript">$disqusJSCode</script>"""

        RawContent(Seq("html"), html)
    }
  }

  val resourcesDir = getClass.getResource("/").getPath
  val targetPath   = Paths.get("target/site")

  // output directory creation
  if (!Files.exists(targetPath)) {
    Files.createDirectory(targetPath)
  }

  // for debugging //
  val testFile = getClass.getResource("/02.md").getPath
  print(
      Transform from Markdown
        .withSpanDirectives(discuss, audioControls) to PrettyPrint fromFile testFile toString
  )
  //

  // html generation
  Transform from Markdown
    .withSpanDirectives(discuss, audioControls) to HTML fromDirectory resourcesDir toDirectory targetPath.toString
}
