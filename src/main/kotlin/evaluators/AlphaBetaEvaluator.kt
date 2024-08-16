package cutelyn.evaluators

import chariot.util.Board
import chariot.util.Board.FromTo
import kotlin.math.max

class AlphaBetaEvaluator(val maxDepth : Int, val baseEvaluator: IBoardEvaluator)  : IBoardEvaluator {

    private fun searchEvaluation(board : Board, level : Int, alpha : Double, beta : Double) : Double {
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
                val moves = board.validMoves()
                val (captures, others) = moves.partition { isCaptureMove(it, board) }
                val orderedMoves = captures + others
                var newAlpha = alpha

                for(move in orderedMoves) {
                    val score = -searchEvaluation(board.play(move), level + 1, -beta, -newAlpha) //if positive for the other player so its negative for us
                    maxScore = max(maxScore, score)
                    newAlpha = max(newAlpha, score)
                    if (newAlpha >= beta) {
                        break
                    }
                }

                maxScore
            }
            else -> 0.00
        }
    }

    override fun evaluate(board: Board): Double {
        return searchEvaluation(board, 1, -2000000.00,  2000000.00)
    }

    private fun isCaptureMove(move: Board.Move, board: Board): Boolean {
        return when(move){
            is FromTo -> board.get(move.to) != null
            else -> false
        }
    }
}