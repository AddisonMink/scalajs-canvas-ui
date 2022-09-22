package amink.canvasui

/** High-level constructors for common ui elements. ComponentUtils should be
  * used instead of creating Components directly.
  */
object ComponentUtils:
  import Component.*
  export Component.*

  case class Padding(left: Int, down: Int)
  case class Border(thickness: Int, color: Color)
  enum AlignmentH { case Left, Center, Right }
  enum AlignmentV { case Top, Center, Bottom }

  /** Specifies formatted text.
    *
    * @param txt
    * @param font
    * @param color
    *   font color
    * @param width
    *   minimum width
    * @param alignment
    *   horizontal alignment
    * @param background
    *   background color
    * @return
    *   Component
    */
  def text(
      txt: String,
      font: Font,
      color: Color,
      width: Int = 0,
      alignment: AlignmentH = AlignmentH.Left,
      background: Option[Color] = None
  ): Component =
    val t = Text(txt, font, color)
    box(t, width, color = background, alignH = alignment)

  /** Specifies an entire image.
    *
    * @param sprite
    * @return
    *   Component
    */
  def sprite(sprite: Sprite): Component =
    Image(
      sprite.canvasWidth,
      sprite.canvasHeight,
      sprite.img,
      0,
      0,
      sprite.img.width,
      sprite.img.height
    )

  /** Specifies a frame of a sprite sheet.
    *
    * @param sprite
    * @param index
    *   (x,y) coorinates giving the number of frames to offset from the top-left
    *   corner of the image.
    * @return
    *   Component
    */
  def spriteFrame(sprite: Sprite, index: (Int, Int)): Component =
    Image(
      sprite.canvasFrameWidth,
      sprite.canvasFrameHeight,
      sprite.img,
      index._1 * sprite.frameWidth,
      index._2 * sprite.frameHeight,
      sprite.frameWidth,
      sprite.frameHeight
    )

  /** General purpose container. Analagous to a div in HTML.
    *
    * A box will stretch to accomidate its contents.
    *
    * @param component
    * @param minWidth
    * @param minHeight
    * @param color
    * @param border
    * @param padding
    * @param alignH
    * @param alignV
    * @param radius
    * @param alpha
    * @return
    *   Component
    */
  def box(
      component: Component,
      minWidth: Int = 0,
      minHeight: Int = 0,
      color: Option[Color] = None,
      border: Option[Border] = None,
      padding: Padding = Padding(0, 0),
      alignH: AlignmentH = AlignmentH.Left,
      alignV: AlignmentV = AlignmentV.Top,
      radius: Int = 0,
      alpha: Double = 1
  ): Component =
    val innerWidth = Math.max(minWidth, component.width)
    val innerHeight = Math.max(minHeight, component.height)
    val paddingWidth = 2 * padding.left
    val paddingHeight = 2 * padding.down
    val borderSize = border.fold(0)(_.thickness) * 2
    val width = innerWidth + paddingWidth + borderSize
    val height = innerHeight + paddingHeight + borderSize

    val x = alignH match
      case AlignmentH.Left   => 0
      case AlignmentH.Right  => width - component.width - padding.left * 2
      case AlignmentH.Center => width / 2 - component.width / 2 - padding.left

    val y = alignV match
      case AlignmentV.Top    => 0
      case AlignmentV.Bottom => height - component.height - padding.down * 2
      case AlignmentV.Center => height / 2 - component.height / 2 - padding.down

    val background = color.map(c => (0, 0, Rect(width, height, c, radius)))
    val frame = border.map(b =>
      (0, 0, Frame(width, height, b.color, b.thickness, radius))
    )
    val trueX = x + padding.left + border.fold(0)(_.thickness)
    val trueY = y + padding.down + border.fold(0)(_.thickness)
    val inner = (trueX, trueY, component)
    val children = List.concat(background, frame, List(inner))

    val cont = Container(width, height, children)
    if alpha == 1 then cont else Alpha(cont, alpha)

  /** Specify a row of components separated by a margin.
    *
    * @param margin
    * @param components
    * @return
    *   Component
    */
  def row(margin: Int, components: List[Component]): Component =
    val height = components.map(_.height).maxOption.getOrElse(0)
    val componentsWidth = components.map(_.width).sum
    val marginWidth = Math.max(0, components.length - 1) * margin
    val width = componentsWidth + marginWidth

    val (_, children) =
      components.foldLeft((0, List.empty[(Int, Int, Component)])) {
        case ((offset, acc), c) =>
          val newOffset = offset + c.width + margin
          (newOffset, acc :+ (offset, 0, c))
      }
    Container(width, height, children)

  /** Specify a column of components separated by a margin.
    *
    * @param margin
    * @param components
    * @return
    *   Component
    */
  def column(margin: Int, components: List[Component]): Component =
    val width = components.map(_.width).maxOption.getOrElse(0)
    val componentsHeight = components.map(_.height).sum
    val marginHeight = Math.max(0, components.length - 1) * margin
    val height = componentsHeight + marginHeight

    val (_, children) =
      components.foldLeft((0, List.empty[(Int, Int, Component)])) {
        case ((offset, acc), c) =>
          val newOffset = offset + c.height + margin
          (newOffset, acc :+ (0, offset, c))
      }
    Container(width, height, children)
