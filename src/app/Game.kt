package app

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

class Game : RComponent<RProps, Game.State>() {

    init {
        state.apply {
            history = listOf(HistoryEntity(MutableList(9) { null }))
            xIsNext = true
            stepNumber = 0
        }
    }

    interface State : RState {
        var history: List<HistoryEntity>
        var xIsNext: Boolean
        var stepNumber: Int
    }

    data class HistoryEntity(val squares: MutableList<String?>)

    private fun handleClick(index: Int) {
        val history = state.history.toList().subList(0, state.stepNumber + 1)
        val current = history.last()
        val squares = current.squares.toMutableList()

        setState {
            this.history = history + HistoryEntity(squares)
            xIsNext = !state.xIsNext
            stepNumber = history.size
        }
    }

    private fun calculateWinner(squares: List<String?>): String? {
        val lines: List<List<Int>> = listOf(
                listOf(0, 1, 2),
                listOf(3, 4, 5),
                listOf(6, 7, 8),
                listOf(0, 3, 6),
                listOf(1, 4, 7),
                listOf(2, 5, 8),
                listOf(0, 4, 8),
                listOf(2, 4, 6)
        )

        lines.forEach { line ->
            val (a, b, c) = line
            if (squares[a] == null) {
                return@forEach
            }
            if (squares[a] != squares[b]) {
                return@forEach
            }
            if (squares[a] != squares[c]) {
                return@forEach
            }
            return squares[a]
        }
        return null
    }

    private fun jumpTo(step: Int) {
        setState {
            stepNumber = stepNumber
            xIsNext = (step % 2) == 0
        }
    }

    private fun RBuilder.moves() = state.history.mapIndexed { step, _ ->
        val desc = if (step == 0) {
            "go to game start"
        } else {
            "Go to move #$step"
        }

        li {
            button {
                +desc
                attrs.onClickFunction = { jumpTo(step) }
            }
        }
    }

    override fun RBuilder.render() {
        val history = state.history
        val current = history[state.stepNumber]
        val winner = calculateWinner(current.squares)
        val status: String = if (winner == null) {
            "Next Player: ${if (state.xIsNext) "X" else "O"}"
        } else {
            "Winner: $winner"
        }

        div(classes = "game") {
            div(classes = "game-board") {
                board(current.squares) {
                    handleClick(it)
                }
            }
            div(classes = "game-info") {
                div { +status }
                ol { moves() }
            }
        }

    }
}

fun RBuilder.game() = child(Game::class) {}