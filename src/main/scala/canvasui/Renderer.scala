package canvasui

import org.scalajs.dom.*

class Renderer(canvas: HTMLCanvasElement):
  private val ctx = canvas
    .getContext("2d")
    .asInstanceOf[CanvasRenderingContext2D]

  def clearIO(color: Color): Unit =
    ctx.fillStyle = color.cssCode
    ctx.fillRect(0, 0, canvas.width, canvas.height)

  def rectIO(x: Int, y: Int, width: Int, height: Int, color: Color): Unit =
    ctx.fillStyle = color.cssCode
    ctx.fillRect(x, y, width, height)

  def rectOutlineIO(
      x: Int,
      y: Int,
      width: Int,
      height: Int,
      thickness: Int,
      color: Color
  ): Unit =
    ctx.strokeStyle = color.cssCode
    ctx.lineWidth = thickness
    ctx.strokeRect(x, y, width, height)

  def roundedRectIO(
      x: Int,
      y: Int,
      width: Int,
      height: Int,
      radius: Int,
      color: Color
  ): Unit =
    ctx.fillStyle = color.cssCode
    roundedRectPathIO(x, y, width, height, radius)
    ctx.fill()

  def roundedRectOutlineIO(
      x: Int,
      y: Int,
      width: Int,
      height: Int,
      thickness: Int,
      radius: Int,
      color: Color
  ): Unit =
    ctx.strokeStyle = color.cssCode
    ctx.lineWidth = thickness
    roundedRectPathIO(x, y, width, height, radius)
    ctx.stroke()

  def measureTextIO(text: String, font: Font): (Int, Int) =
    ctx.font = font.cssCode
    val width = ctx.measureText(text).width.toInt
    (width, font.lineHeight)

  def textIO(x: Int, y: Int, text: String, font: Font, color: Color): Unit =
    ctx.fillStyle = color.cssCode
    val trueY = y + font.lineHeight + font.baseLineOffset
    ctx.fillText(text, x, trueY)

  private def roundedRectPathIO(
      x: Int,
      y: Int,
      width: Int,
      height: Int,
      radius: Int
  ): Unit =
    ctx.beginPath()
    ctx.moveTo(x + radius, y)
    ctx.lineTo(x + width - radius, y)
    ctx.quadraticCurveTo(x + width, y, x + width, y + radius)
    ctx.lineTo(x + width, y + height - radius)
    ctx.quadraticCurveTo(x + width, y + height, x + width - radius, y + height)
    ctx.lineTo(x + radius, y + height)
    ctx.quadraticCurveTo(x, y + height, x, y + height - radius)
    ctx.lineTo(x, y + radius)
    ctx.quadraticCurveTo(x, y, x + radius, y)
