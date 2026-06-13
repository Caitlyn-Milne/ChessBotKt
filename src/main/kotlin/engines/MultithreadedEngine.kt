package cutelyn.engines

import chariot.util.Board
import cutelyn.Board.BoardCoordinate
import cutelyn.Util.validMovesWithPromotions
import cutelyn.evaluators.IBoardEvaluator
import kotlinx.coroutines.*

class MultithreadedEngine(val evaluator: IBoardEvaluator)  : IChessEngine {
    override fun calculateMove(board : Board): Board.Move = runBlocking {
        withContext(Dispatchers.Default) {
            val deferredScores = board.validMovesWithPromotions().map { move ->
                async {
                    val boardAfterMove = board.play(move)
                    val score = -evaluator.evaluate(boardAfterMove, boardAfterMove.validMoves(), 4, coroutineContext)
                    Pair<Board.Move, Double>(move, score)
                }
            }
            val scores = deferredScores.awaitAll()

            val (move, score) = scores.maxBy { it.second }
            println("Best Move: ${move} Best score: ${score}")
            move
        }
    }

    override fun calculateMoveForDebugging(board: Board, move: Board.Move): Double {
        return evaluator.evaluate(board, setOf(move), 4, Dispatchers.Main)
    }
}