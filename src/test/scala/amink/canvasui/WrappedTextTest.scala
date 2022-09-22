package amink.canvasui

import scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.*

import ComponentUtils._

@JSExportTopLevel("WrappedTextTest")
object WrappedTextTest extends App:
  try {
    def canvas(id: String) = document
      .getElementById(id)
      .asInstanceOf[HTMLCanvasElement]

    def renderer(id: String) =
      Renderer(canvas(id))

    wrappedText(
      "hello, hello, hello",
      Font.Monospace(16),
      Color.black,
      150,
      0,
      None
    )
      .renderIO(5, 5)(renderer("wrapped-text"))

    wrappedText(
      "hello, hello, hello",
      Font.Monospace(16),
      Color.white,
      150,
      0,
      Some(Color.black)
    )
      .renderIO(5, 5)(renderer("highlighted-wrapped-text"))
  } catch { case _ => }
