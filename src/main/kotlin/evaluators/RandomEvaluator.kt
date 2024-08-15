package cutelyn.evaluators

import chariot.util.Board
import java.util.Random

class RandomEvaluator : IBoardEvaluator {
    private val random : Random = Random()
    override fun evaluate(board: Board): Double {
        return random.nextDouble()
    }
}