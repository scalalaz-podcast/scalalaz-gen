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

package ru.scalalaz.gen.parsing

import org.scalatest.{ FlatSpec, Inside, Matchers }

class FormatParserSpec extends FlatSpec with Matchers with Inside {

  val raw = """title=value
      |key2=value2
      |----
      |### Yoyoyo!
      |it is a new episode!""".stripMargin

  /*it should "parse from string" in {
    val result = FormatParser.parseContent(raw)
    inside(result) {
      case Right(parsed) =>
        parsed.header shouldBe Map("title" -> "value", "key2" -> "value2")
        parsed.otherData shouldBe "### Yoyoyo!\nit is a new episode!"
    }
  }*/

  val raw2 =
    """
      |title=Выпуск 01
      |enc.url=https://scalalaz.ru/mp3/scalalaz-podcast-1.mp3
      |enc.length=63337733
      |page=http://scalalaz.ru/series-01.html
      |date=2016-08-07
      |----
      |### Выпуск 01
      |
      |@:audioControls "https://scalalaz.ru/mp3/scalalaz-podcast-1.mp3".
      |
      |Темы:
      |
      |- [Релиз-кандидат Akka](http://akka.io/news/2016/08/02/akka-…4.9-RC1-released.html)
      |- [Релиз Spark](https://exit.sc/?url=http%3A%2F%2Fspark.apache.org%2Freleases%2Fspark-release-2-0-0.html)
      |- [Релиз Spark](https://exit.sc/?url=http%3A%2F%2Fspark.apache.org%2Freleases%2Fspark-release-2-0-0.html)
      |- [Релиз Spark](https://exit.sc/?url=http%3A%2F%2Fspark.apache.org%2Freleases%2Fspark-release-2-0-0.html)
      |- [Pants](http://www.pantsbuild.org/)""".stripMargin

  it should "parse more complicated case" in {
    val result = FormatParser.parseContent(raw2)
    inside(result) {
      case Right(parsed) =>
    }
  }
}
