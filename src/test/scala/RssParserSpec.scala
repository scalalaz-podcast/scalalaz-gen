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

import fastparse.all._
import fastparse.core.Parsed

import collection.mutable.Stack
import org.scalatest._
import ru.scalalaz.gen.Rss
import ru.scalalaz.gen.Rss._

class RssSpec extends FlatSpec with Matchers {

  "rss tag start" should "be parsed" in {
    val parsed = start.parse("@:rssTagStart tag=title:")

    val r = parsed match {
      case Parsed.Success(_, _)    => true
      case Parsed.Failure(_, _, _) => false
    }

    r shouldBe true
  }

  "rss tag end" should "be parsed" in {
    val parsed = end.parse("@:rssTagEnd tag=title.")

    val r = parsed match {
      case Parsed.Success(_, _)    => true
      case Parsed.Failure(_, _, _) => false
    }

    r shouldBe true
  }

  "one line rss tag sequense" should "be parsed" in {
    val parsed = content.parse(
        """@:rssTagStart tag=title: Выпуск 02 @:rssTagEnd tag=title."""
    )

    val r = parsed match {
      case Parsed.Success(_, _)    => true
      case Parsed.Failure(_, _, _) => false
    }

    r shouldBe true
  }

  "rssTag parser" should "parse single rss tag with content" in {
    val parsed = rssTag.parse(
        """@:rssTagStart tag=title:
                   |Ссылки:
                   |
                   |- [Scaladays 2016](http://manuel.bernhardt.io/2016/08/09/akka-anti-patterns-flat-actor-hierarchies-or-mixing-business-logic-and-failure-handling/)
                   |- [Eff Monad](http://bit.ly/eff_flatmap_2016). [https://github.com/atnos-org/eff-scalaz](https://github.com/atnos-org/eff-scalaz)
                   |- [Principles of Elegance - by Jon Pretty](https://www.youtube.com/watch?v=bUO_oLwe4Og)
                   |- [Roll Your Own Shapeless - by Daniel Spiewak](https://www.youtube.com/watch?v=GKIfu1WtSz4)
                   |
                   |@:rssTagEnd tag=title.""".stripMargin
    )

    val r = parsed match {
      case Parsed.Success(_, _)    => true
      case Parsed.Failure(_, _, _) => false
    }

    r shouldBe true
  }

  "parser" should "parse markdown with few rss tags" in {
    val parsed = markdown.parse(
        """Paragraph:
        |
        |@:disqus.
        |
        |@:rssTagStart tag=title:
        |    Выпуск 02
        |@:rssTagEnd tag=title.
        |
        |@:rssTagStart tag=description:
        |    Темы:
        |
        |    - [Scala library index](http://scala-lang.org/blog/2016/08/09/the-scala-library-index-reaches-beta.html)
        |    - [Scala js](http://www.lihaoyi.com/post/FromfirstprinciplesWhyIbetonScalajs.html)
        |    - [August sip meeting results](http://www.scala-lang.org/blog/2016/08/15/sip-meeting-august-results.html) |
        |@:rssTagEnd tag=description.
        |
        |@:disqus.
        |""".stripMargin
    )

    println(parsed)

    val r = parsed match {
      case Parsed.Success(_, _)    => true
      case Parsed.Failure(_, _, _) => false
    }

    r shouldBe true
  }

  "parser" should "parse markdown without rss tags" in {
    val parsed = markdown.parse(
        """Paragraph:
          |### Выпуск 02
          |
          |@:audioControls "http://scalalaz.ru/mp3/scalalaz-podcast-2.mp3".
          |
          |[Запись](http://scalalaz.ru/mp3/scalalaz-podcast-2.mp3)
          |
          |Темы:
          |
          |- [Scala library index](http://scala-lang.org/blog/2016/08/09/the-scala-library-index-reaches-beta.html)
          |- [Scala js](http://www.lihaoyi.com/post/FromfirstprinciplesWhyIbetonScalajs.html)
          |- [August sip meeting results](http://www.scala-lang.org/blog/2016/08/15/sip-meeting-august-results.html)
          |- [Akka anti patterns](http://manuel.bernhardt.io/2016/08/09/akka-anti-patterns-flat-actor-hierarchies-or-mixing-business-logic-and-failure-handling/)
          |- О Scaladays 2016
          |- Зачем Lagom
          |
          |События:
          |
          |- [Moscow Scala Meetup #4](https://data-monsters.timepad.ru/event/360185/)
          |
          |Ссылки:
          |@:disqus.
          |
          |""".stripMargin
    )

    val r = parsed match {
      case Parsed.Success(_, _)    => true
      case Parsed.Failure(_, _, _) => false
    }

    r shouldBe true
  }

}
