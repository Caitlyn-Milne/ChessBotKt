package cutelyn.evaluators

import chariot.util.Board
import kotlin.coroutines.CoroutineContext

// Evaluators return a score for a given board state, from the perspective of who's turn it is
interface IBoardEvaluator {
    fun evaluate(board : Board, moves : Set<Board.Move>, maxDepth : Int, coroutineContext: CoroutineContext) : Double

    fun evaluateForDebugging(board : Board, move : Board.Move, maxDepth: Int) : Double = 0.00
}