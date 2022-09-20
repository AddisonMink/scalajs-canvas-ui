package canvasui

import org.scalajs.dom.*

class Renderer(canvas: HTMLCanvasElement):
  private val ctx = canvas
    .getContext("2d")
    .asInstanceOf[CanvasRenderingContext2D]

  def clearIO(color: Color): Unit =
    ctx.fillStyle = color.cssCode
    ctx.fillRect(0, 0, canvas.width, canvas.height)
