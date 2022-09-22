package amink.canvasui

import org.scalajs.dom.HTMLImageElement
import scala.scalajs.js.annotation.JSExportTopLevel

/** A component is a unit of content that can be rendered to the canvas.
  */
enum Component(val width: Int, val height: Int):
  import Component._

  case Text(
      text: String,
      font: Font,
      color: Color
  ) extends Component(font.textWidth(text), font.lineHeight)

  case Image(
      img: HTMLImageElement,
      imgX: Int,
      imgY: Int,
      imgWidth: Int,
      imgHeight: Int,
      override val width: Int,
      override val height: Int
  ) extends Component(width, height)

  case Container(
      override val width: Int,
      override val height: Int,
      color: Option[Color],
      border: Option[Component.Border],
      radius: Int,
      alpha: Double,
      components: List[(Component.Point, Component)]
  ) extends Component(width, height)

  case Empty extends Component(0, 0)

  def renderIO(x: Int, y: Int)(r: Renderer): Unit =
    _renderIO(this, x, y, r)

object Component:
  case class Point(x: Int, y: Int)
  case class Border(thickness: Int, color: Color)

  private def _renderIO(
      component: Component,
      x: Int,
      y: Int,
      renderer: Renderer
  ): Unit =
    component match
      case Text(text, font, color) =>
        renderer.textIO(x, y, text, font, color)

      case Image(img, imgX, imgY, imgWidth, imgHeight, width, height) =>
        renderer.imageIO(
          x,
          y,
          width,
          height,
          imgX,
          imgY,
          imgWidth,
          imgHeight,
          img
        )

      case Container(width, height, color, border, radius, alpha, components) =>
        renderer.setAlphaIO(alpha)
        color.foreach(renderer.rectIO(x, y, width, height, radius, _))

        border.foreach(b =>
          renderer.rectOutlineIO(
            x,
            y,
            width,
            height,
            b.thickness,
            radius,
            b.color
          )
        )

        components.foreach { case (Point(ox, oy), c) =>
          _renderIO(c, x + ox, y + oy, renderer)
        }

        renderer.resetAlphaIO()

      case Empty =>
