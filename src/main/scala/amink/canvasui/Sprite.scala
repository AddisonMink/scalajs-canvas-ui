package amink.canvasui

import org.scalajs.dom.HTMLImageElement

final case class Sprite(
    name: String,
    img: HTMLImageElement,
    canvasWidth: Int,
    canvasHeight: Int,
    frameWidth: Int,
    frameHeight: Int,
    canvasFrameWidth: Int,
    canvasFrameHeight: Int
)
