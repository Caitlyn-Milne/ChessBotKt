package cutelyn.engines

import chariot.util.Board
import chariot.util.Board.Move
import chariot.util.Board.PieceType
import cutelyn.evaluators.IEvaluator
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class SimpleDepthFirstSearchEngine(val side : Board.Side, val evaluator: IEvaluator, val depth : Int = 3) : IChessEngine {

    override fun calculateMove(board: Board): Board.Move {

        fun dfs(board : Board, level : Int) : Double {
            val even = level % 2 == 0

            if(board.gameState() == Board.GameState.draw_by_threefold_repetition
                || board.gameState() == Board.GameState.draw_by_fifty_move_rule
                || board.gameState() == Board.GameState.stalemate) {
                return if(even) -500000.00 else 500000.00
            }

            if(!board.validMoves().any())
                return if(even) -1000000.00 else 1000000.00

            if(level >= depth) {
                val evalScore = evaluator.evaluate(board)
                return if(even) -evalScore else evalScore
            }

            var maxScore = -1000000.00

            for(move in board.validMoves()) {
                var score = -dfs(board.play(move), level + 1)
                maxScore = max(maxScore, score)
            }

            return maxScore
        }

        var bestMove : Board.Move = board.validMoves().first()
        var bestScore = -1000000.00
        for(move in board.validMoves()) {
            val score = -dfs(board.play(move), 1)
            if(score > bestScore){
                bestScore = score
                bestMove = move
            }
        }
        val roundedScore = (bestScore * 100).roundToInt().toDouble() / 100
        println("Best Move: ${bestMove}; Best Score: ${roundedScore}")
        return bestMove
    }
}