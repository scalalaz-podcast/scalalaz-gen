package ru.scalalaz.gen

import java.nio.file.{Files, Path}

import scala.collection.JavaConversions._

object fs {

  def list(p: Path): Seq[Path] =
    p.toFile.list().map(p.resolve)

  def clean(path: Path): Unit = {
    val file = path.toFile
    if (file.isDirectory) {
      list(path).foreach(clean)
      file.delete()
    } else
      file.delete()
  }

  def createDir(path: Path): Unit = {
    val (root, tail) = (path.head, path.tail)
    val dirs = tail.scanLeft(root)((p, n) => p.resolve(n))
    dirs.foreach(d => if (!d.toFile.exists()) Files.createDirectory(d))
  }

  def copyFile(from: Path, to: Path): Unit = {
    val data = Files.readAllBytes(from)
    Files.write(to, data)
  }

  def copyDir(from: Path, to: Path): Unit = copyDir(from, to, _ => true)

  def copyDir(from: Path, to: Path, filter: Path => Boolean): Unit = {
    list(from).filter(filter)
      .foreach(p => {
        if (p.toFile.isFile)
          copyFile(p, to.resolve(p.last))
        else {
          val next = to.resolve(p.last)
          createDir(to.resolve(p.last))
          copyDir(p, next, filter)
        }
      })
  }
}
