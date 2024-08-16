package cutelyn.engines

import chariot.util.Board
import cutelyn.evaluators.IBoardEvaluator
import kotlinx.coroutines.*

class MultithreadedEngine(val evaluator: IBoardEvaluator)  : IChessEngine {
    override fun calculateMove(board : Board): Board.Move = runBlocking {
        withContext(Dispatchers.Default) {
            val deferredScores = board.validMoves().map { move ->
                async {
                    val boardAfterMove = board.play(move)
                    val score = -evaluator.evaluate(boardAfterMove, boardAfterMove.validMoves())
                    Pair<Board.Move, Double>(move, score)
                }
            }
            val scores = deferredScores.awaitAll()

            val (move, score) = scores.maxBy { it.second }
            println("Best Move: ${move} Best score: ${score}")
            move
        }
    }
}