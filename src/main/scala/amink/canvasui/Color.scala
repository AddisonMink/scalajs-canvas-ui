package amink.canvasui

final case class Color(
    r: Int,
    g: Int,
    b: Int
):
  val cssCode: String = s"rgb($r,$g,$b)"

object Color:
  val black = Color(0, 0, 0)
  val white = Color(255, 255, 255)
  val red = Color(255, 0, 0)
  val green = Color(0, 255, 0)
  val blue = Color(0, 0, 255)
