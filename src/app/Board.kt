package app

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class Board: RComponent<Board.Props, RState>() {

    interface Props: RProps {
        var squares: MutableList<String?>
        var onClickFunction: (Int) -> Unit
    }

    private fun RBuilder.renderSquare(index: Int) {
        square(
                value = props.squares[index],
                onClickFunction = { props.onClickFunction(index)}
        )
    }

    override fun RBuilder.render() {
        div {
            div(classes = "board-row") {
                renderSquare(0)
                renderSquare(1)
                renderSquare(2)
            }
            div(classes = "board-row") {
                renderSquare(3)
                renderSquare(4)
                renderSquare(5)
            }
            div(classes = "board-row") {
                renderSquare(6)
                renderSquare(7)
                renderSquare(8)
            }
        }
    }
}

fun RBuilder.board(squares: MutableList<String?>, onClickFunction: (Int) -> Unit) =
        child(Board::class) {
            attrs.squares = squares
            attrs.onClickFunction = onClickFunction
        }