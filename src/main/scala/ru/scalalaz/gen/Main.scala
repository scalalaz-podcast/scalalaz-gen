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

package ru.scalalaz.gen

import java.nio.file.Paths

object Main extends App {

  val markdownDir = Paths.get("src/main/resources/md")
  val targetPath  = Paths.get("target/site")

  val siteSettings = SiteSettings()

  val gen = new Generator(siteSettings, markdownDir, targetPath)
  val pgen = new SpecialPagesGenerator(markdownDir, targetPath)

  gen.generate() match {
    case Left(error) =>
      println("Main Pages generation failed, error:")
      println(error)
      sys.exit(1)
    case _ =>
      println("Main Pages generation - Done")
  }

  pgen.generate() match {
    case Left(error) =>
      println("Special Pages generation failed, error:")
      println(error)
      sys.exit(1)
    case _ =>
      println("Special Pages generation - Done")
  }

}
