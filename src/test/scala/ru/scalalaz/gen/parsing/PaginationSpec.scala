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

import org.scalatest.freespec.AnyFreeSpec
import ru.scalalaz.gen.writers.Pagination
import ru.scalalaz.gen.writers.Pagination.Button

class PaginationSpec extends AnyFreeSpec {

  "Pagination.from" - {
    "should be able to construct empty pagination" in {
      assert(Pagination.from(0, List.empty[Int]) === Pagination[Int](List.empty))
    }

    "should fail on invalid currentPageIndex" - {
      "when currentPageIndex is less than 0" in {
        assertThrows[AssertionError] {
          Pagination.from(-1, List(1))
        }
      }

      "when currentPageIndex is bigger than allPages.size" in {
        assertThrows[AssertionError] {
          Pagination.from(2, List(1))
        }
      }
    }

    "should correctly build pagination object" - {
      testPattern("1!|2|3|4|5|6|7")
      testPattern("1|2!|3|4|5|6|7")
      testPattern("1|2|3!|4|5|6|7")
      testPattern("1|2|3|4!|5|6|7")
      testPattern("1|2|3|4|5!|6|7")
      testPattern("1|2|3|4|5|6!|7")
      testPattern("1|2|3|4|5|6|7!")

      testPattern("1!|2|3|4|.|7|8")

      testPattern("1!|2|3|4|.|14|15")
      testPattern("1|2!|3|4|5|.|14|15")
      testPattern("1|2|3!|4|5|6|.|14|15")
      testPattern("1|2|3|4!|5|6|7|.|14|15")
      testPattern("1|2|3|4|5!|6|7|8|.|14|15")
      testPattern("1|2|3|4|5|6!|7|8|9|.|14|15")
      testPattern("1|2|3|4|5|6|7!|8|9|10|.|14|15")
      testPattern("1|2|.|5|6|7|8!|9|10|11|.|14|15")
      testPattern("1|2|.|6|7|8|9!|10|11|12|13|14|15")
      testPattern("1|2|.|7|8|9|10!|11|12|13|14|15")
      testPattern("1|2|.|8|9|10|11!|12|13|14|15")
      testPattern("1|2|.|9|10|11|12!|13|14|15")
      testPattern("1|2|.|10|11|12|13!|14|15")
      testPattern("1|2|.|11|12|13|14!|15")
      testPattern("1|2|.|12|13|14|15!")
    }
  }

  private def testPattern(expectedPattern: String): Unit = {
    s"with pattern $expectedPattern" in {
      def invalidPattern = throw new RuntimeException(s"Invalid pattern: $expectedPattern")

      val expectedButtons: Vector[Pagination.Button[String]] = {
        expectedPattern.split('|').toVector.map { section =>
          if (section.endsWith("!")) {
            Pagination.Button.CurrentPage(section.dropRight(1))
          } else if (section == ".") {
            Pagination.Button.Ellipsis
          } else {
            Pagination.Button.Page(section)
          }
        }
      }

      val currentPageIndex = expectedButtons.collectFirst {
        case Pagination.Button.CurrentPage(value) => value.toIntOption.getOrElse(invalidPattern) - 1
      }.getOrElse(invalidPattern)

      def toIntValue(button: Button[String]): Int = {
        (button match {
          case Button.CurrentPage(value) => value
          case Button.Page(value) => value
          case Button.Ellipsis => invalidPattern
        }).toIntOption.getOrElse(invalidPattern)
      }

      val first: Int = toIntValue(expectedButtons.head)
      val last: Int = toIntValue(expectedButtons.last)

      val actual = Pagination.from(currentPageIndex, (first to last).toList.map(_.toString))

      assert(actual == Pagination(expectedButtons))
    }
  }
}
