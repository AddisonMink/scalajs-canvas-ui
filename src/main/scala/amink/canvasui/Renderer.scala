package amink.canvasui

import org.scalajs.dom.*

final class Renderer(canvas: HTMLCanvasElement):
  private val ctx = canvas
    .getContext("2d")
    .asInstanceOf[CanvasRenderingContext2D]

  def clearIO(color: Color): Unit =
    ctx.fillStyle = color.cssCode
    ctx.fillRect(0, 0, canvas.width, canvas.height)

  def rectIO(
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

  def rectOutlineIO(
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

  def textIO(x: Int, y: Int, text: String, font: Font, color: Color): Unit =
    ctx.fillStyle = color.cssCode
    ctx.font = font.cssCode
    val trueY = y + font.lineHeight + font.baseLineOffset
    ctx.fillText(text, x, trueY)

  def imageIO(
      x: Int,
      y: Int,
      width: Int,
      height: Int,
      imgX: Int,
      imgY: Int,
      imgWidth: Int,
      imgHeight: Int,
      img: HTMLImageElement
  ): Unit =
    ctx.drawImage(
      img,
      imgX,
      imgY,
      imgWidth,
      imgHeight,
      x,
      y,
      width,
      height
    )

  def setAlphaIO(alpha: Double): Unit =
    ctx.globalAlpha = alpha

  def resetAlphaIO(): Unit =
    setAlphaIO(1)

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
