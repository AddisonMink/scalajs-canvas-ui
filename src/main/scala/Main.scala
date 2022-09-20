import canvasui.*

import org.scalajs.dom.*

@main def main: Unit =
  clearDemo()

def clearDemo(): Unit =
  val canvas = org.scalajs.dom.document
    .getElementById("clear-demo")
    .asInstanceOf[HTMLCanvasElement]

  val renderer = Renderer(canvas)
  renderer.clearIO(Color.black)
