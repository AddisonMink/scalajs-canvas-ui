package amink.canvasui

enum Font(
    val size: Int,
    name: String,
    descent: Double,
    halfLeading: Double,
    widthToHeightRatio: Double
):

  case Monospace(
      override val size: Int
  ) extends Font(size, "monospace", 0.25, 0.25, 0.60009765625)

  val cssCode: String = s"${size}px $name"

  val lineHeight: Int =
    Math.round(size + descent + 2 * halfLeading).toInt

  val charWidth: Double = widthToHeightRatio * size

  def textWidth(text: String): Int =
    Math.round(text.length * charWidth).toInt

  val baseLineOffset: Int =
    -Math.round(size * descent).toInt
