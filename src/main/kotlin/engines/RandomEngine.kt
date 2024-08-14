package cutelyn.engines

import chariot.util.Board
import java.util.Random

class RandomEngine : IChessEngine {
    private val random : Random = Random()

    override fun calculateMove(board: Board): Board.Move {
        val randomIndex = random.nextInt(0, board.validMoves().size)
        return board.validMoves().elementAt(randomIndex)
    }
}