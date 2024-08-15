package cutelyn.evaluators

import chariot.util.Board
import kotlin.math.max

class SimpleDfsEvaluator(val maxDepth : Int, val baseEvaluator: IBoardEvaluator) : IBoardEvaluator {

    private fun searchEvaluation(board : Board, level : Int) : Double {
        return when(board.gameState()!!) {
            Board.GameState.checkmate -> {
                -1000000.00
            }
            Board.GameState.stalemate,
            Board.GameState.draw_by_fifty_move_rule,
            Board.GameState.draw_by_threefold_repetition -> {
                -500000.00
            }
            Board.GameState.ongoing -> {
                if(level == maxDepth)
                    return baseEvaluator.evaluate(board)

                var maxScore = -1000000.00

                for(move in board.validMoves()) {
                    val score = -searchEvaluation(board.play(move), level + 1) //if positive for the other player so its negative for us
                    maxScore = max(maxScore, score)
                }

                maxScore
            }
            else -> 0.00
        }
    }

    override fun evaluate(board: Board): Double {
        return searchEvaluation(board, 1)
    }
}