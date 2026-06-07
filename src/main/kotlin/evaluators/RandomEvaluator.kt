package cutelyn.evaluators

import chariot.util.Board
import chariot.util.Board.Move
import java.util.Random
import kotlin.coroutines.CoroutineContext

class RandomEvaluator : IBoardEvaluator {
    private val random : Random = Random()
    override fun evaluate(board: Board, moves : Set<Move>, maxDepth : Int, coroutineContext: CoroutineContext): Double {
        return random.nextDouble()
    }
}