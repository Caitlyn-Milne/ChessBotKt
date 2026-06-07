package cutelyn.engines

import chariot.util.Board
import cutelyn.evaluators.IBoardEvaluator
import kotlinx.coroutines.*
import java.util.Hashtable
import kotlin.time.Duration.Companion.milliseconds

class NaiveIterativeDeepeningEngine(val evaluator: IBoardEvaluator)  : IChessEngine {
    override fun calculateMove(board : Board): Board.Move = runBlocking {
        withContext(Dispatchers.Default) {
            val movesAndScores = Hashtable<Board.Move, Double>()
            for(boardMove in board.validMoves()) {
                movesAndScores[boardMove] = 0.0
            }
            var depth = 1
            val timeLimit = System.currentTimeMillis() + 5000
            while (isActive) {
                val sortedMoves = movesAndScores.keys.sortedBy { -(movesAndScores[it] ?: 0.00) }
                val deferredScores = sortedMoves.map { move ->
                    async {
                        this.coroutineContext
                        val boardAfterMove = board.play(move)
                        val score = -evaluator.evaluate(boardAfterMove, boardAfterMove.validMoves(), depth, coroutineContext)
                        movesAndScores[move] = score
                    }
                }
                do {
                    delay(100.milliseconds)
                    if(System.currentTimeMillis() > timeLimit) {
                        deferredScores.forEach { it.cancelAndJoin() }
                    }
                }
                while (deferredScores.any {it.isActive})
                depth++
            }

            val bestMove = movesAndScores.keys.maxBy { movesAndScores[it] ?: 0.00 }
            println("Best Move: $bestMove Best score: ${movesAndScores[bestMove]}")
            bestMove
        }
    }
}