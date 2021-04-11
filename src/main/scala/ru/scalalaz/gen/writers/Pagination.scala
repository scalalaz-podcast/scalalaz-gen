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

case class Pagination[A](
  buttons: Seq[Pagination.Button[A]]
)
object Pagination {

  sealed trait Button[+A]
  object Button {
    case class CurrentPage[A](value: A) extends Button[A]
    case class Page[A](value: A) extends Button[A]
    case object Ellipsis extends Button[Nothing]
  }

  case class Config(
    maxNextButtons: Int = 3,
    maxEdgeButtons: Int = 2,
  ) {
    def allFitsSize: Int = maxNextButtons + maxEdgeButtons + 2
  }

  def from[A](
    currentPageIndex: Int,
    allPages: Seq[A],
    config: Config = Config(),
  ): Pagination[A] = {
    val size = allPages.size

    if (size == 0) {
      Pagination[A](List.empty)
    } else {
      assert(
        currentPageIndex >= 0 && currentPageIndex < size,
        s"Invalid currentPageIndex $currentPageIndex. It should be in the interval [0,${size})."
      )

      val allButtons: Vector[Button[A]] = allPages.toVector.zipWithIndex.map { case (page, idx) =>
        if (idx == currentPageIndex) {
          Button.CurrentPage(page)
        } else {
          Button.Page(page)
        }
      }

      val buttons: Seq[Button[A]] = if (size <= config.allFitsSize) {
        allButtons
      } else {
        val (beforeCurrent, withCurrent) = allButtons.splitAt(currentPageIndex)
        val afterCurrent = withCurrent.drop(1)

        val current: Button[A] = withCurrent.head

        val rightFragment = computeRightFragment(afterCurrent, config)
        val leftFragment = computeRightFragment(beforeCurrent.reverse, config).reverse

        leftFragment ++ Vector(current) ++ rightFragment
      }

      Pagination(buttons)
    }
  }

  private def computeRightFragment[A](
    fragment: Vector[Button[A]],
    config: Config,
  ): Vector[Button[A]] = {
    val (nextButtons, afterNextButtons) = fragment.splitAt(config.maxNextButtons)

    val (rightEllipsisButtons, lastButtons) = {
      if (afterNextButtons.size > config.maxEdgeButtons) {
        afterNextButtons.splitAt(afterNextButtons.size - config.maxEdgeButtons)
      } else {
        (Vector.empty, afterNextButtons)
      }
    }

    val rightEllipsis = if (rightEllipsisButtons.size > 1) {
      Vector(Button.Ellipsis)
    } else {
      rightEllipsisButtons
    }

    nextButtons ++ rightEllipsis ++ lastButtons
  }
}
