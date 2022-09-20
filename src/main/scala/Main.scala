import canvasui.*

import org.scalajs.dom.*

@main def main: Unit =
  clearDemoIO()
  rectDemoIO()
  rectOutlineDemoIO()
  roundedRectDemoIO()
  roundedRectOutlineDemoIO()
  monospaceTextDemoIO()
  textDemoIO()

  val img = document
    .createElement("img")
    .asInstanceOf[HTMLImageElement]

  val sprite = Sprite("sprite", img, 100, 100, 200, 200, 100, 100)
  img.onload = { _ =>
    spriteDemoIO(sprite)
    spriteFrameDemoIO(sprite)
  }
  img.src = "images/goblin.png"

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

def monospaceTextDemoIO(): Unit =
  val renderer = Renderer(canvasIO("monospace-text-demo"))
  renderer.clearIO(Color.white)
  val text = "Greetings, world!"
  val font = Font.Monospace(16)
  val (width, height) = renderer.measureTextIO(text, font)
  renderer.rectIO(0, 0, width, height, Color.black)
  renderer.textIO(0, 0, text, font, Color.white)

def spriteDemoIO(sprite: Sprite): Unit =
  val renderer = Renderer(canvasIO("sprite-demo"))
  renderer.clearIO(Color.black)
  renderer.spriteIO(0, 0, sprite)

def spriteFrameDemoIO(sprite: Sprite): Unit =
  val renderer = Renderer(canvasIO("sprite-frame-demo"))
  renderer.clearIO(Color.black)
  renderer.spriteFrameIO(0, 0, sprite, (1, 1))

def textDemoIO(): Unit =
  val renderer = Renderer(canvasIO("c-text-demo"))
  val style = Style(Color.black, Color.white, Font.Monospace(16), 5, 5)
  val text = Component.Text("Greetings, world!", style)
  text.renderIO(0, 0)(renderer)

def canvasIO(id: String): HTMLCanvasElement =
  document
    .getElementById(id)
    .asInstanceOf[HTMLCanvasElement]
