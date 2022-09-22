package amink.canvasui

import scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.*

import ComponentUtils._

@JSExportTopLevel("ContainerTest")
object ContainerTest extends App:
  try {
    def canvas(id: String) = document
      .getElementById(id)
      .asInstanceOf[HTMLCanvasElement]

    def renderer(id: String) =
      Renderer(canvas(id))

    val emptyCont: Component.Container =
      container(300, 300, Nil, Some(Color.black))
        .asInstanceOf[Component.Container]
    emptyCont.renderIO(0, 0)(renderer("empty-container"))

    val txt = text("hello", Font.Monospace(16), Color.white)
    val cont = emptyCont.copy(
      components =
        List(Component.Point(0, 0) -> txt, Component.Point(50, 50) -> txt),
      color = Some(Color.black)
    )
    cont.renderIO(5, 5)(renderer("non-empty-container"))

    val contWithBorder =
      cont.copy(
        width = 100,
        height = 100,
        border = Some(Component.Border(5, Color.white))
      )
    renderer("container-with-border").clearIO(Color.black)
    contWithBorder.renderIO(10, 10)(renderer("container-with-border"))

    val contWithRadius =
      contWithBorder
        .copy(radius = 5)
        .asInstanceOf[Component.Container]
    renderer("container-with-radius").clearIO(Color.black)
    contWithRadius.renderIO(10, 10)(renderer("container-with-radius"))

    val contWithAlpha = contWithRadius.copy(alpha = 0.5)
    renderer("container-with-alpha").clearIO(Color.black)
    contWithAlpha.renderIO(10, 10)(renderer("container-with-alpha"))
  } catch {
    case _ =>
  }
