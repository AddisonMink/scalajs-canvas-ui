package canvasui

final case class Style(
    color: Color,
    backgroundColor: Color,
    font: Font,
    rowMargin: Int,
    columnMargin: Int,
    padding: Style.Padding = Style.Padding(0, 0),
    border: Option[Style.Border] = None
)

object Style:
  final case class Padding(left: Int, down: Int)
  final case class Border(color: Color, thickness: Int)
