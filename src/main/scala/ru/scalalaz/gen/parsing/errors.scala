package ru.scalalaz.gen.parsing

import java.nio.file.Path

import cats.data.NonEmptyList

sealed trait EpisodeParseError

case class MissingKey(name: String) extends EpisodeParseError

case class InvalidDate(description: String) extends EpisodeParseError

case class InvalidFormat(explanation: String) extends EpisodeParseError

case class ManyErrors(list: NonEmptyList[EpisodeParseError]) extends EpisodeParseError

case class FileParseError(path: Path, manyErrors: EpisodeParseError) extends EpisodeParseError

