package cutelyn.evaluators

import chariot.util.Board
import chariot.util.Board.Move
import java.util.Random

class RandomEvaluator : IBoardEvaluator {
    private val random : Random = Random()
    override fun evaluate(board: Board, moves : Set<Move>): Double {
        return random.nextDouble()
    }
}