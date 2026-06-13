package cutelyn.engines

import chariot.util.Board
import cutelyn.Util.validMovesWithPromotions
import cutelyn.evaluators.IBoardEvaluator
import kotlinx.coroutines.Dispatchers
import sun.launcher.resources.launcher
import kotlin.coroutines.coroutineContext

class SingleThreadedEngine(val evaluator: IBoardEvaluator) : IChessEngine {
    override fun calculateMove(board : Board): Board.Move {
        var bestMove : Board.Move = board.validMovesWithPromotions().first()
        var bestScore = -1000000.00

        for(move in board.validMoves()) {
            val boardAfterMove = board.play(move)
            val score = evaluator.evaluate(boardAfterMove, boardAfterMove.validMoves(), 4, Dispatchers.Main)
            if(score > bestScore){
                bestScore = score
                bestMove = move
            }
        }
        println("Best Move: ${bestMove} Best score: ${bestScore}")
        return bestMove
    }

    override fun calculateMoveForDebugging(board: Board, move: Board.Move): Double {
        return evaluator.evaluate(board, setOf(move), 4, Dispatchers.Main)
    }
}