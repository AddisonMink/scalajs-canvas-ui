package amink.canvasui

import scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.*

import ComponentUtils._

@JSExportTopLevel("TextTest")
object TextTest extends App:
  try {
    def canvas(id: String) = document
      .getElementById(id)
      .asInstanceOf[HTMLCanvasElement]

    def renderer(id: String) =
      Renderer(canvas(id))

    text("hello", Font.Monospace(16), Color.black, None)
      .renderIO(5, 5)(renderer("plain-text"))

    text("hello", Font.Monospace(16), Color.white, Some(Color.black))
      .renderIO(5, 5)(renderer("highlighted-text"))

    text("hello", Font.Monospace(40), Color.black, None)
      .renderIO(5, 5)(renderer("big-plain-text"))

    text("hello", Font.Monospace(40), Color.white, Some(Color.black))
      .renderIO(5, 5)(renderer("big-highlighted-text"))
  } catch {
    case _ =>
  }
