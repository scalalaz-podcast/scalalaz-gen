package ru.scalalaz.gen

import java.nio.file.{Files, Path, Paths}

import cats.data.Validated
import ru.scalalaz.gen.parsing.{EpisodeParseError, EpisodeParser, FileParseError}

object Main extends App {

  val markdownDir   = Paths.get(getClass.getResource("/md").getPath)
  val targetPath    = Paths.get("target/site")
  val targetRssPath = Paths.get("target/site/rss")

  fs.clean(targetPath)
  fs.createDir(targetRssPath)

  val isEpisode = (p: Path) => p.toFile.getName.startsWith("series-")

  val parsed = fs.list(markdownDir).filter(isEpisode)
    .map(p => {
      val bytes = Files.readAllBytes(p)
      val content = new String(bytes)
      EpisodeParser.fromString(content)
        .map(e => EpisodeFile(p, e))
        .leftMap(e => FileParseError(p, e))
    })

  if (parsed.exists(_.isInvalid)) {
    reportErrors(parsed)
    sys.exit(1)
  }

  fs.copyDir(markdownDir, targetPath, isEpisode.andThen(!_))

  private def reportErrors(results: Seq[Validated[EpisodeParseError, EpisodeFile]]): Unit = {
    results.filter(_.isInvalid)
      .foreach(inv => {
        val msg = s"Error occurred while parsing file:\n\t $inv"
        println(msg)
      })
  }

}
