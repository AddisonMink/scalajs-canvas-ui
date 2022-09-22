package amink.canvasui

import scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.*

import ComponentUtils._

@JSExportTopLevel("StretchBoxTest")
object StretchBoxTest extends App:
  try {
    def canvas(id: String) = document
      .getElementById(id)
      .asInstanceOf[HTMLCanvasElement]

    def renderer(id: String) =
      Renderer(canvas(id))

    
  } catch {
    case _ =>
  }
