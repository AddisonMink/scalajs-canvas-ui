package amink.canvasui

import scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.*

import ComponentUtils._

@JSExportTopLevel("SpriteTest")
object SpriteTest extends App:
  try {
    def canvas(id: String) = document
      .getElementById(id)
      .asInstanceOf[HTMLCanvasElement]

    def renderer(id: String) =
      Renderer(canvas(id))

    val image = document
      .createElement("img")
      .asInstanceOf[HTMLImageElement]

    val sprt = Sprite("ghoul", image, 128, 96, 16, 16, 32, 32)
    image.src = "images/ghoul.png"

    image.onload = _ =>
      sprite(sprt)
        .renderIO(5, 5)(renderer("sprite"))
      spriteFrame(sprt, Component.Point(1, 1))
        .renderIO(5,5)(renderer("sprite-frame"))

  } catch { case _ => }
