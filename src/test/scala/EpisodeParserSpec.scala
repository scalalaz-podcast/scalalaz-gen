import org.scalatest.{FlatSpec, Inside, Matchers}
import ru.scalalaz.gen.EpisodeParser

class EpisodeParserSpec extends FlatSpec with Matchers with Inside {

  val episodeStr =
    """
      |key=value
      |key2=value2
      |----
      |### Yoyoyo!
      |it is a new episode!""".stripMargin

  it should "parse from string" in {
    val parsed = EpisodeParser.fromString(episodeStr)
    inside(parsed) {
      case Left(ep) =>
        ep.сontent shouldBe "### Yoyoyo!\nit is a new episode!"
    }
  }

}
