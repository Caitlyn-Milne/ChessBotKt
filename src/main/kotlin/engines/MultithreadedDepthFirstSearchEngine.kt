package cutelyn.engines

import chariot.util.Board
import chariot.util.Board.PieceType
import cutelyn.evaluators.IEvaluator
import kotlin.math.max
import kotlin.math.min
import kotlinx.coroutines.*
import java.util.*
import kotlin.math.roundToInt
import kotlin.system.measureTimeMillis

class MultithreadedDepthFirstSearchEngine(val evaluator: IEvaluator, val d : Int = 3) : IChessEngine {

    override fun calculateMove(board: Board): Board.Move {
        var move: Board.Move? = null
        var depth = d
        do {
            var time = measureTimeMillis {
                move = calculateMove(board, depth)
            }
            if(depth == 7)
                break
            println("depth ${depth++}")
        } while (time < 750)

        return move!!
    }

    fun calculateMove(board: Board, depth: Int): Board.Move {

        fun dfs(board : Board, level : Int) : Double {

            if(board.gameState() == Board.GameState.draw_by_threefold_repetition
                || board.gameState() == Board.GameState.draw_by_fifty_move_rule
                || board.gameState() == Board.GameState.stalemate) {
                return -500000.00
            }

            if(!board.validMoves().any())
                return -1000000.00

            if(level >= depth) {
                val evalScore = evaluator.evaluate(board)
                return evalScore
            }

            var maxScore = -1000000.00

            for(move in board.validMoves()) {
                var score = -dfs(board.play(move), level + 1)
                maxScore = max(maxScore, score)
            }

            return maxScore
        }
        val counterContext = newSingleThreadContext("Ctx")
        val multithreadContext = newFixedThreadPoolContext(20, "Ctx2")
        var bestMove: Board.Move = board.validMoves().first()
        var bestScore = -1000000.00
        runBlocking {
            withContext(multithreadContext) {
                for (move in board.validMoves()) {
                    launch {
                        val score = -dfs(board.play(move), 1)
                        withContext(counterContext) {
                            if (score > bestScore) {
                                bestScore = score
                                bestMove = move
                            }
                        }
                    }
                }
            }
        }
        val roundedScore = (bestScore * 100).roundToInt().toDouble() / 100
        println("Best Move: ${bestMove}; Best Score: ${roundedScore}")
        return bestMove
    }

}
