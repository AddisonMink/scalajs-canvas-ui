package canvasui

enum Component:
  import Component._

  case Text(
      text: String,
      style: Style = Style()
  )

  case StretchBox(
      minWidth: Int = 0,
      minHeight: Int = 0,
      contents: Option[Component] = None,
      style: Style = Style()
  )

  case FreeBox(
      width: Int = 0,
      height: Int = 0,
      contents: List[((Int, Int), Component)] = Nil,
      style: Style = Style()
  )

  case Column(
      contents: List[Component] = Nil,
      style: Style = Style()
  )

  case Row(
      contents: List[Component] = Nil,
      style: Style = Style()
  )

  /* A Renderer is required to get a Component's
   * Size. The value needs to be memoized in order
   * to avoid a lot of redundant computation.
   */
  private var size: Option[Size] = None

  def sizeIO(r: Renderer): Size = size match
    case Some(s) => s
    case None =>
      size = Some(computeSizeIO(r))
      size.get

  private def computeSizeIO(r: Renderer): Size =
    this match
      case Text(text, style) =>
        val (w, h) = r.measureTextIO(text, style.font)
        Size(w, h)

      case StretchBox(minWidth, minHeight, contents, style) =>
        val Size(cWidth, cHeight) =
          contents.fold(Size(minWidth, minHeight))(_.sizeIO(r))

        val paddingWidth = style.padding.left * 2
        val paddingHeight = style.padding.down * 2
        val borderWidth = style.border.fold(0)(_.thickness)
        val borderHeight = borderWidth
        val width = Math.max(cWidth, minWidth) + paddingWidth + borderWidth
        val height = Math.max(cHeight, minHeight) + paddingHeight + borderHeight
        Size(width, height)

      case FreeBox(width, height, _, _) => Size(width, height)

      case Column(contents, style) =>
        val width = contents.map(_.sizeIO(r).width).maxOption.getOrElse(0)
        val contentsHeight = contents.map(_.sizeIO(r).height).sum
        val marginHeight = Math.max(0, contents.length - 1) * style.columnMargin
        val height = contentsHeight + marginHeight
        Size(width, height)

      case Row(contents, style) =>
        val contentsWidth = contents.map(_.sizeIO(r).width).sum
        val marginWidth = Math.max(0, contents.length - 1) * style.rowMargin
        val width = contentsWidth + marginWidth
        val height = contents.map(_.sizeIO(r).height).maxOption.getOrElse(0)
        Size(width, height)

  def renderIO(x: Int, y: Int)(r: Renderer): Unit =
    this match
      case Text(text, style) =>
        r.textIO(x, y, text, style.font, style.color)
        val (w, h) = r.measureTextIO(text, style.font)

      case StretchBox(minWidth, minHeight, contents, style) =>
        val Size(cWidth, cHeight) = contents.fold(Size(0, 0))(_.sizeIO(r))
        val innerWidth = Math.max(cWidth, minWidth) + style.padding.left * 2
        val innerHeight = Math.max(cHeight, minHeight) + style.padding.down * 2

        if style.radius == 0 then
          r.rectIO(x, y, innerWidth, innerHeight, style.backgroundColor)
        else
          r.roundedRectIO(
            x,
            y,
            innerWidth,
            innerHeight,
            style.radius,
            style.backgroundColor
          )

        (style.border, style.radius) match
          case (Some(b), 0) =>
            r.rectOutlineIO(x, y, innerWidth, innerHeight, b.thickness, b.color)
          case (Some(b), radius) =>
            r.roundedRectOutlineIO(
              x,
              y,
              innerWidth,
              innerHeight,
              b.thickness,
              radius,
              b.color
            )
          case _ =>

        contents.foreach(
          _.renderIO(x + style.padding.left, y + style.padding.down)(r)
        )

      case FreeBox(width, height, contents, style) =>
        contents.foreach { case ((offsetX, offsetY), c) =>
          c.renderIO(x + offsetX, y + offsetY)(r)
        }

      case Column(contents, style) =>
        contents.foldLeft(0) { (offset, c) =>
          c.renderIO(x, y + offset)(r)
          offset + c.sizeIO(r).height + style.columnMargin
        }

      case Row(contents, style) =>
        contents.foldLeft(0) { (offset, c) =>
          c.renderIO(x + offset, y)(r)
          offset + c.sizeIO(r).width + style.rowMargin
        }

object Component:
  case class Size(width: Int, height: Int)
