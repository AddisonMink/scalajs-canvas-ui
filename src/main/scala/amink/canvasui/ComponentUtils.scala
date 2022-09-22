package amink.canvasui

import Component._

/** High-level constructors for common ui elements. */
object ComponentUtils:

  case class Padding(left: Int, down: Int)

  enum AlignmentH { case Left, Center, Right }
  enum AlignmentV { case Top, Center, Bottom }

  def text(
      txt: String,
      font: Font,
      color: Color,
      background: Option[Color] = None
  ): Component =
    val t = Text(txt, font, color)
    background match
      case None    => t
      case Some(c) => stretchBox(t, color = Some(c))

  // TODO Wrap text without breaking up words.
  def wrappedText(
      txt: String,
      font: Font,
      color: Color,
      width: Int,
      lineMargin: Int = 0,
      background: Option[Color] = None
  ): Component =
    val charsPerLine = Math.round(width.toDouble / font.charWidth).toInt
    val lines = txt.grouped(charsPerLine).toList
    val ts = lines.map(text(_, font, color, background))
    column(lineMargin, ts)

  def sprite(sprite: Sprite): Component =
    Image(
      sprite.img,
      0,
      0,
      sprite.img.width,
      sprite.img.height,
      sprite.canvasWidth,
      sprite.canvasHeight
    )

  def spriteFrame(sprite: Sprite, index: Point): Component =
    Image(
      sprite.img,
      index.x * sprite.frameWidth,
      index.y * sprite.frameHeight,
      sprite.frameWidth,
      sprite.frameHeight,
      sprite.canvasFrameWidth,
      sprite.canvasFrameHeight
    )

  def container(
      width: Int,
      height: Int,
      components: List[(Point, Component)],
      color: Option[Color] = None,
      border: Option[Border] = None,
      radius: Int = 0,
      alpha: Double = 1
  ): Component =
    Container(width, height, color, border, radius, alpha, components)

  def stretchBox(
      component: Component,
      minWidth: Int = 0,
      minHeight: Int = 0,
      color: Option[Color] = None,
      border: Option[Border] = None,
      padding: Padding = Padding(0, 0),
      radius: Int = 0,
      alpha: Double = 1
  ): Component =
    val innerWidth = Math.max(minWidth, component.width)
    val innerHeight = Math.max(minHeight, component.height)
    val paddingWidth = 2 * padding.left
    val paddingHeight = 2 * padding.down
    val borderSize = border.fold(0)(_.thickness)
    val width = innerWidth + paddingWidth + borderSize
    val height = innerHeight + paddingHeight + borderSize
    val origin = Point(padding.left, padding.down)
    Container(
      width,
      height,
      color,
      border,
      radius,
      alpha,
      List(origin -> component)
    )

  def fixedBox(
      width: Int,
      height: Int,
      component: Component,
      color: Option[Color],
      border: Option[Border] = None,
      padding: Padding = Padding(0, 0),
      radius: Int = 0,
      alpha: Double = 1,
      alignV: AlignmentV = AlignmentV.Center,
      alignH: AlignmentH = AlignmentH.Center
  ): Component =
    val x = alignH match
      case AlignmentH.Left   => padding.left
      case AlignmentH.Right  => width - component.width - padding.left
      case AlignmentH.Center => width / 2 - component.width / 2

    val y = alignV match
      case AlignmentV.Top    => padding.down
      case AlignmentV.Bottom => height - component.height - padding.down
      case AlignmentV.Center => height / 2 - component.width / 2

    val origin = Point(x, y)
    Container(
      width,
      height,
      color,
      border,
      radius,
      alpha,
      List(origin -> component)
    )

  def row(margin: Int, components: List[Component]): Component =
    val height = components.map(_.height).maxOption.getOrElse(0)
    val componentsWidth = components.map(_.width).sum
    val marginWidth = Math.max(0, components.length - 1) * margin
    val width = componentsWidth + marginWidth

    val (_, points) = components.foldLeft((0, List.empty[Point])) {
      case ((offset, acc), c) =>
        val point = Point(offset, 0)
        val newOffset = offset + c.width + margin
        (newOffset, acc :+ point)
    }

    Container(width, height, None, None, 0, 1, points.zip(components))

  def column(margin: Int, components: List[Component]): Component =
    val width = components.map(_.width).maxOption.getOrElse(0)
    val componentsHeight = components.map(_.height).sum
    val marginHeight = Math.max(0, components.length - 1) * margin
    val height = componentsHeight + marginHeight

    val (_, points) = components.foldLeft((0, List.empty[Point])) {
      case ((offset, acc), c) =>
        val point = Point(0, offset)
        val newOffset = offset + c.height + margin
        (newOffset, acc :+ point)
    }

    Container(width, height, None, None, 0, 1, points.zip(components))

  def layers(components: List[Component]): Component =
    val width = components.map(_.width).maxOption.getOrElse(0)
    val height = components.map(_.height).maxOption.getOrElse(0)
    val cs = components.map(Point(0, 0) -> _)
    Container(width, height, None, None, 0, 1, cs)
