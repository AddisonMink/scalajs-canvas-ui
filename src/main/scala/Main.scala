import canvasui.*

import org.scalajs.dom.*

@main def main: Unit =
  clearDemoIO()
  rectDemoIO()
  rectOutlineDemoIO()
  roundedRectDemoIO()
  roundedRectOutlineDemoIO()

def clearDemoIO(): Unit =
  val renderer = Renderer(canvasIO("clear-demo"))
  renderer.clearIO(Color.black)

def rectDemoIO(): Unit =
  val renderer = Renderer(canvasIO("rect-demo"))
  renderer.clearIO(Color.black)
  renderer.rectIO(25, 40, 75, 100, Color.white)

def rectOutlineDemoIO(): Unit =
  val renderer = Renderer(canvasIO("rect-outline-demo"))
  renderer.clearIO(Color.black)
  renderer.rectOutlineIO(25, 40, 75, 100, 5, Color.white)

def roundedRectDemoIO(): Unit =
  val renderer = Renderer(canvasIO("rounded-rect-demo"))
  renderer.clearIO(Color.black)
  renderer.roundedRectIO(25, 40, 75, 100, 5, Color.white)

def roundedRectOutlineDemoIO(): Unit =
  val renderer = Renderer(canvasIO("rounded-rect-outline-demo"))
  renderer.clearIO(Color.black)
  renderer.roundedRectOutlineIO(25, 40, 75, 100, 5, 5, Color.white)

def canvasIO(id: String): HTMLCanvasElement =
  document
    .getElementById(id)
    .asInstanceOf[HTMLCanvasElement]
