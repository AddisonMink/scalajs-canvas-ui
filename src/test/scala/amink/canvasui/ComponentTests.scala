package amink.canvasui

import org.scalajs.dom.*
import scalajs.js.annotation.JSExportTopLevel

import Component.*

@JSExportTopLevel("ComponentTests")
object ComponentTests:

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
    Renderer(canvas)

  Component.Empty.renderIO(0, 0)(renderer("empty"))

  Component
    .Text("Greetings, world!", Font.Monospace(16), Color.black)
    .renderIO(0, 0)(renderer("text", width = 200))

  Component
    .Text("Greetings, world!", Font.Monospace(40), Color.black)
    .renderIO(0, 0)(renderer("big-text", width = 200))

  val img = document
    .createElement("img")
    .asInstanceOf[HTMLImageElement]

  img.onload = _ =>
    Component
      .Image(128, 96, img, 0, 0, 64, 48, None)
      .renderIO(0, 0)(renderer("image", width = 200))
    Component
      .Image(128, 96, img, 0, 0, 64, 48, Some(Color.red))
      .renderIO(0, 0)(renderer("image-tint", width = 200))
    Component
      .Image(32, 32, img, 16, 16, 16, 16, None)
      .renderIO(0, 0)(renderer("image-slice"))
  img.src = "images/ghoul.png"

  Component
    .Rect(50, 50, Color.black)
    .renderIO(5, 5)(renderer("rect"))

  Component
    .Rect(50, 50, Color.black, radius = 5)
    .renderIO(5, 5)(renderer("rounded-rect"))

  Component
    .Frame(50, 50, Color.black, 5)
    .renderIO(5, 5)(renderer("frame"))

  Component
    .Frame(50, 50, Color.black, 5, radius = 5)
    .renderIO(5, 5)(renderer("rounded-frame"))

  Component
    .Container(
      100,
      100,
      List(
        (10, 10, Component.Text("test", Font.Monospace(16), Color.black)),
        (50, 50, Component.Text("test", Font.Monospace(16), Color.black))
      )
    )
    .renderIO(10, 10)(renderer("container"))

  Component
    .Alpha(
      Component.Text("test", Font.Monospace(16), Color.black),
      0.5
    )
    .renderIO(0, 0)(renderer("alpha"))
