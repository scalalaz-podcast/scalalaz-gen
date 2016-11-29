package ru.scalalaz.gen

import java.io.File
import java.nio.file.{Files, Path, Paths}

import cats.data.Validated
import ru.scalalaz.gen.parsing.{EpisodeParseError, EpisodeParser}

import scala.collection.JavaConversions._

object Main extends App {

  val markdownDir   = getClass.getResource("/md").getPath
  val targetPath    = Paths.get("target/site")
  val targetRssPath = Paths.get("target/site/rss")

  cleanDir(targetPath)
  createDir(targetRssPath)

  val parsed = EpisodeParser.fromDirectory(markdownDir)
  if (parsed.exists(_.isInvalid)) {
    reportErrors(parsed)
    sys.exit(1)
  }

//  def mdFiles: Seq[File] = {
//    Paths.get(dir).toFile.list()
//      .map(Paths.get(_).toFile)
//      .filter(f => !f.isDirectory && f.getName.endsWith(".md"))
//  }

  private def reportErrors(results: Seq[Validated[EpisodeParseError, EpisodeFile]]): Unit = {
    parsed.filter(_.isInvalid)
      .foreach(inv => {
        val msg = s"Error occured while parsing file:\n\t $inv"
        println(msg)
      })
  }

  private def cleanDir(path: Path): Unit = {
    val file = path.toFile
    if (file.isDirectory) {
      file.list().map(path.resolve).foreach(cleanDir)
      file.delete()
    } else
      file.delete()
  }

  private def createDir(path: Path): Unit = createDir(path.iterator())

  private def createDir(iter: Iterator[Path]): Unit =
    if (iter.hasNext) {
      val p = iter.next()
      if (!p.toFile.exists()) Files.createDirectory(p)
      createDir(iter)
    }

}
