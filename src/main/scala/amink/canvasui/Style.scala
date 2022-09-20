package amink.canvasui

final case class Style(
    color: Color = Color.black,
    backgroundColor: Color = Color.white,
    font: Font = Font.Monospace(16),
    rowMargin: Int = 0,
    columnMargin: Int = 0,
    padding: Style.Padding = Style.Padding(0, 0),
    border: Option[Style.Border] = None,
    radius: Int = 0
)

object Style:
  final case class Padding(left: Int, down: Int)
  final case class Border(color: Color, thickness: Int)
