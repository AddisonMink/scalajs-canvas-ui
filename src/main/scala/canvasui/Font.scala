package canvasui

enum Font(val size: Int, name: String, descent: Double, halfLeading: Double):

  case Monospace(
      override val size: Int
  ) extends Font(size, "monospace", 0.25, 0.25)

  val cssCode: String = s"${size}px $name"

  val lineHeight: Int =
    (size + descent + 2 * halfLeading).toInt

  val baseLineOffset: Int =
    -(size * descent).toInt
