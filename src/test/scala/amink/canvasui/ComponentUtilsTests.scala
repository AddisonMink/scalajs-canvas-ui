package amink.canvasui

import org.scalajs.dom.*
import scalajs.js.annotation.JSExportTopLevel

import Component.*

@JSExportTopLevel("ComponentUtilsTests")
object ComponentUtilsTests:
  import ComponentUtils.*

  private def renderer(
      id: String,
      width: Int = 100,
      height: Int = 100
  ): Renderer =
    val canvas = document
      .getElementById(id)
      .asInstanceOf[HTMLCanvasElement]
    canvas.width = width
    canvas.height = height
    canvas.style.backgroundColor = "black"
    Renderer(canvas)

  text("Greetings, world!", Font.Monospace(16), Color.white)
    .renderIO(0, 0)(renderer("c-text", width = 300))

  text(
    "Greetings, world!",
    Font.Monospace(16),
    Color.black,
    background = Some(Color.white)
  )
    .renderIO(0, 0)(renderer("c-text-background", width = 300))

  text(
    "Greetings, world!",
    Font.Monospace(16),
    Color.white,
    alignment = AlignmentH.Center,
    width = 300
  )
    .renderIO(0, 0)(renderer("c-text-centered", width = 300))

  text(
    "Greetings, world!",
    Font.Monospace(16),
    Color.white,
    alignment = AlignmentH.Right,
    width = 300
  )
    .renderIO(0, 0)(renderer("c-text-right", width = 300))

  val img = document
    .createElement("img")
    .asInstanceOf[HTMLImageElement]

  img.onload = _ =>
    val s = Sprite("test", img, 128, 96, 16, 16, 32, 32)
    println(sprite(s))

    sprite(s)
      .renderIO(0, 0)(renderer("c-sprite", width = 200))
    spriteFrame(s, (1, 1))
      .renderIO(0, 0)(renderer("c-sprite-frame"))
  img.src = "images/ghoul.png"

  box(Empty, 100, 100, color = Some(Color.blue))
    .renderIO(5, 5)(renderer("c-box", width = 200, height = 200))

  box(
    Empty,
    100,
    100,
    color = Some(Color.blue),
    border = Some(Border(5, Color.white))
  )
    .renderIO(5, 5)(renderer("c-box-border", width = 200, height = 200))

  box(Empty, 100, 100, color = Some(Color.blue), radius = 10)
    .renderIO(5, 5)(renderer("c-box-rounded", width = 200, height = 200))

  box(
    Empty,
    100,
    100,
    color = Some(Color.blue),
    border = Some(Border(5, Color.white)),
    radius = 10
  )
    .renderIO(5, 5)(renderer("c-box-border-rounded", width = 200, height = 200))

  box(
    text("test", Font.Monospace(16), Color.white),
    100,
    100,
    color = Some(Color.blue),
    padding = Padding(10, 10)
  ).renderIO(0, 0)(renderer("c-box-padding", width = 200, height = 200))

  box(
    text("test", Font.Monospace(16), Color.white),
    100,
    100,
    color = Some(Color.blue),
    alignH = AlignmentH.Center,
    padding = Padding(10, 10)
  ).renderIO(0, 0)(renderer("c-box-h-center", width = 200, height = 200))

  box(
    text("test", Font.Monospace(16), Color.white),
    100,
    100,
    color = Some(Color.blue),
    alignH = AlignmentH.Right,
    padding = Padding(10, 10)
  ).renderIO(0, 0)(renderer("c-box-right", width = 200, height = 200))

  box(
    text("test", Font.Monospace(16), Color.white),
    100,
    100,
    color = Some(Color.blue),
    alignV = AlignmentV.Center,
    padding = Padding(10, 10)
  ).renderIO(0, 0)(renderer("c-box-v-center", width = 200, height = 200))

  box(
    text("test", Font.Monospace(16), Color.white),
    100,
    100,
    color = Some(Color.blue),
    alignV = AlignmentV.Bottom,
    padding = Padding(10, 10)
  ).renderIO(0, 0)(renderer("c-box-bottom", width = 200, height = 200))

  box(
    text("test", Font.Monospace(16), Color.white),
    100,
    100,
    color = Some(Color.blue),
    alpha = 0.5
  ).renderIO(0, 0)(renderer("c-box-alpha", width = 200, height = 200))

  box(
    text("test", Font.Monospace(16), Color.white),
    color = Some(Color.blue),
    alignV = AlignmentV.Center
  ).renderIO(0, 0)(renderer("c-box-shrink", width = 200, height = 200))

  box(
    box(Empty, 100, 100, Some(Color.blue), radius = 10),
    100,
    100,
    color = Some(Color.white)
  ).renderIO(0, 0)(renderer("c-box-size", width = 200, height = 200))

  box(
    box(
      Empty,
      100,
      100,
      Some(Color.red),      
      radius = 10
    ),
    100,
    100,
    color = Some(Color.blue),
    Some(Border(5, Color.white)),
  ).renderIO(0, 0)(renderer("c-box-border-align", width = 200, height = 200))

  row(5, Nil).renderIO(0, 0)(renderer("c-row-empty"))

  row(
    5,
    List(
      box(Empty, 100, 100, Some(Color.blue)),
      box(Empty, 100, 100, Some(Color.blue))
    )
  ).renderIO(0, 0)(renderer("c-row", width = 500))

  column(5, Nil).renderIO(0, 0)(renderer("c-col-empty"))

  column(
    5,
    List(
      box(Empty, 100, 100, Some(Color.blue)),
      box(Empty, 100, 100, Some(Color.blue))
    )
  ).renderIO(0, 0)(renderer("c-col", height = 500))
