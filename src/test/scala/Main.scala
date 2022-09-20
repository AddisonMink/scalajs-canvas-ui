import amink.canvasui.*

import org.scalajs.dom.*

def main: Unit =
  clearDemoIO()
  rectDemoIO()
  rectOutlineDemoIO()
  roundedRectDemoIO()
  roundedRectOutlineDemoIO()
  monospaceTextDemoIO()
  textDemoIO()
  stretchBoxDemoIO()
  columnDemoIO()
  rowDemoIO()
  freeBoxDemoIO()
  alphaDemoIO()

  val img = document
    .createElement("img")
    .asInstanceOf[HTMLImageElement]

  val sprite = Sprite("sprite", img, 100, 100, 200, 200, 100, 100)
  img.onload = { _ =>
    spriteDemoIO(sprite)
    spriteFrameDemoIO(sprite)
    spriteComponentDemoIO(sprite)
    spriteFrameComponentDemoIO(sprite)
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
  val renderer = Renderer(canvasIO("sprite-demo"), Map(sprite.name -> sprite))
  renderer.clearIO(Color.black)
  renderer.spriteIO(0, 0, sprite.name)

def spriteFrameDemoIO(sprite: Sprite): Unit =
  val renderer = Renderer(
    canvasIO("sprite-frame-demo"),
    Map(sprite.name -> sprite)
  )

  renderer.clearIO(Color.black)
  renderer.spriteFrameIO(0, 0, sprite.name, (1, 1))

def textDemoIO(): Unit =
  val renderer = Renderer(canvasIO("c-text-demo"))
  val text = Component.Text("Greetings, world!")
  text.renderIO(0, 0)(renderer)

def stretchBoxDemoIO(): Unit =
  val renderer = Renderer(canvasIO("c-stretch-box-demo"))

  val style = Style(
    backgroundColor = Color.black,
    color = Color.white,
    padding = Style.Padding(5, 5),
    border = Some(Style.Border(Color.white, 5)),
    radius = 5
  )

  val box = Component.StretchBox(
    contents = Some(
      Component.Text("Greetings, world!", style)
    ),
    style = style
  )

  renderer.clearIO(Color.black)
  box.renderIO(5, 5)(renderer)

def columnDemoIO(): Unit =
  val renderer = Renderer(canvasIO("c-column-demo"))

  val style =
    Style(backgroundColor = Color.black, color = Color.white, columnMargin = 5)

  val text = Component.Text("Greetings!", style)
  val text2 = Component.Text("Greeting, world!", style)
  val column = Component.Column(List(text, text2, text), style)
  val box = Component.StretchBox(contents = Some(column), style = style)
  box.renderIO(5, 5)(renderer)

def rowDemoIO(): Unit =
  val canvas = canvasIO("c-row-demo")
  canvas.width = 500
  val renderer = Renderer(canvas)

  val style =
    Style(backgroundColor = Color.black, color = Color.white, columnMargin = 5)

  val text = Component.Text("Greetings!", style)
  val text2 = Component.Text("Greeting, world!", style)
  val row = Component.Row(List(text, text2, text), style)
  val box = Component.StretchBox(contents = Some(row), style = style)
  box.renderIO(5, 5)(renderer)

def freeBoxDemoIO(): Unit =
  val renderer = Renderer(canvasIO("c-free-box-demo"))
  val text = Component.Text("Greetings, world!")

  val box = Component.FreeBox(
    300,
    300,
    List(
      ((50, 50), text),
      ((60, 60), text)
    )
  )

  box.renderIO(10, 5)(renderer)

def spriteComponentDemoIO(sprite: Sprite): Unit =
  val renderer = Renderer(canvasIO("c-sprite-demo"), Map(sprite.name -> sprite))

  val spriteComponent =
    Component.Sprite(sprite.name, sprite.canvasWidth, sprite.canvasHeight)

  spriteComponent.renderIO(5, 5)(renderer)

def spriteFrameComponentDemoIO(sprite: Sprite): Unit =
  val renderer = Renderer(
    canvasIO("c-sprite-frame-demo"),
    Map(sprite.name -> sprite)
  )

  val spriteFrameComponent = Component.SpriteFrame(
    sprite.name,
    sprite.canvasFrameWidth,
    sprite.canvasFrameHeight,
    (1, 1)
  )

  spriteFrameComponent.renderIO(5, 5)(renderer)

def alphaDemoIO(): Unit =
  val renderer = Renderer(canvasIO("c-alpha-demo"))
  val text = Component.Text("Greetings, world!")
  val alpha = Component.Alpha(Some(text), 0.5)
  alpha.renderIO(5, 5)(renderer)

def canvasIO(id: String): HTMLCanvasElement =
  document
    .getElementById(id)
    .asInstanceOf[HTMLCanvasElement]
