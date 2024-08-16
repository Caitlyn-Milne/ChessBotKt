package cutelyn.evaluators

import chariot.util.Board
import chariot.util.Board.FromTo
import kotlin.math.max

class AlphaBetaEvaluator(val maxDepth : Int, val baseEvaluator: IBoardEvaluator)  : IBoardEvaluator {

    private fun searchEvaluation(board : Board, moves : Set<Board.Move>, level : Int, alpha : Double, beta : Double) : Double {
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
                    return baseEvaluator.evaluate(board,moves)

                var maxScore = -1000000.00
                val (captures, others) = moves.partition { isCaptureMove(it, board) }
                val orderedMoves = captures + others
                var newAlpha = alpha

                for(move in orderedMoves) {
                    val boardAfterMove = board.play(move)
                    val score = -searchEvaluation(boardAfterMove, boardAfterMove.validMoves(),level + 1, -beta, -newAlpha) //if positive for the other player so its negative for us
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

    private fun isCaptureMove(move: Board.Move, board: Board): Boolean {
        return when(move){
            is FromTo -> board.get(move.to) != null
            else -> false
        }
    }

    override fun evaluate(board: Board, moves : Set<Board.Move>): Double {
        return searchEvaluation(board, moves, 1, -2000000.00,  2000000.00)
    }
}