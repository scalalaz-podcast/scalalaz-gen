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

/*
import com.typesafe.config.ConfigFactory
import laika.directive.Directives.{ Default, _ }
import laika.tree.Elements._
import laika.util.Builders._

trait Directives {

  val config     = ConfigFactory.load()
  val disqusCode = config.getString("disqus.disqusCode")
  val siteUrl    = config.getString("scalalazUrl")

  val audioControlTagName = "audioControls"
  val disqusTagName       = "disqus"
  val rssTagName          = "rss"

  lazy val audioControlsBlock: Blocks.Directive =
    Blocks.create(audioControlTagName) {
      import Blocks.Combinators._

      (attribute(Default) ~ attribute("type").optional ~ body(Default).optional) {
        (src, `type`, content) =>
          val t = `type`.getOrElse("mpeg")

          RawContent(Seq("html"), s"""|<audio controls="" class="audio-panel">
                                      |<source src="$src" type="audio/$t">
                                      |</audio>""".stripMargin)
      }
    }

  lazy val discussBlock: Blocks.Directive = Blocks.create(disqusTagName) {
    import Blocks.Combinators._

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

  lazy val rssStartBlock: Blocks.Directive = Blocks.create("rssTagStart") {
    import Blocks.Combinators._

    (attribute("tag") ~ attribute("attr").optional ~ body(Default).optional) {
      (value, attr, content) =>
        RawContent(Seq("html"), "") // empty block
    }
  }

  lazy val rssEndBlock: Blocks.Directive = Blocks.create("rssTagEnd") {
    import Blocks.Combinators._

    (attribute("tag") ~ body(Default).optional) { (value, content) =>
      RawContent(Seq("html"), "") // empty block
    }
  }
}

object Directives extends Directives
 */
