package canvasui

enum Component:
  case Text(text: String, style: Style)

  def renderIO(x: Int, y: Int)(r: Renderer): Unit =
    this match
      case Text(text, style) =>
        r.textIO(x, y, text, style.font, style.color)

