package amink.canvasui

import org.scalajs.dom.HTMLImageElement
import scala.scalajs.js.annotation.JSExportTopLevel

/** Component is an ADT that represents entities that can can be rendered to the
  * canvas.
  *
  * Components can be rendered to the screen with their renderIO method.
  *
  * Currently supported primitives are:
  *   - Empty, the zero-value of Component algebra.
  *   - Text, specifies plain, colored text.
  *   - Image, specifies a rectangular slice of an image.
  *   - Rect, species a colored rectangle with a corner radius.
  *   - Frame, speecifies a colored rectangular frame with a corner radius.
  *
  * Currently supported combinators are:
  *   - Container, contains a set of components and specifies where to render
  *     the components relative to the Container's origin.
  *   - Alpha, specifies to render a component with a given alpha value.
  */
enum Component(val width: Int, val height: Int):
  import Component._

  case Empty extends Component(0, 0)

  case Text(
      text: String,
      font: Font,
      color: Color
  ) extends Component(font.textWidth(text), font.lineHeight)

  case Image(
      override val width: Int,
      override val height: Int,
      img: HTMLImageElement,
      imgX: Int,
      imgY: Int,
      imgWidth: Int,
      imgHeight: Int,
      tint: Option[Color]
  ) extends Component(width, height)

  case Rect(
      override val width: Int,
      override val height: Int,
      color: Color,
      radius: Int = 0
  ) extends Component(width, height)

  case Frame(
      override val width: Int,
      override val height: Int,
      color: Color,
      thickness: Int,
      radius: Int = 0
  ) extends Component(width, height)

  case Container(
      override val width: Int,
      override val height: Int,
      components: List[(Int, Int, Component)]
  ) extends Component(width, height)

  case Alpha(
      component: Component,
      alpha: Double
  ) extends Component(component.width, component.height)

  // TODO This can be refactored to be tail-recursive.
  def renderIO(x: Int, y: Int)(r: Renderer): Unit =
    this match
      case Empty =>

      case Text(text, font, color) =>
        r.textIO(x, y, text, font, color)

      case Image(width, height, img, imgX, imgY, imgWidth, imgHeight, tint) =>
        r.imageIO(
          x,
          y,
          width,
          height,
          imgX,
          imgY,
          imgWidth,
          imgHeight,
          img,
          tint
        )

      case Rect(width, height, color, radius) =>
        r.rectIO(x, y, width, height, radius, color)

      case Frame(width, height, color, thickness, radius) =>
        r.rectOutlineIO(x, y, width, height, thickness, radius, color)

      case Container(width, height, components) =>
        components.foreach((ox, oy, c) => c.renderIO(x + ox, y + oy)(r))

      case Alpha(component, alpha) =>
        r.setAlphaIO(alpha)
        component.renderIO(x, y)(r)
        r.resetAlphaIO()
