package cutelyn

import chariot.util.Board
import cutelyn.engines.IChessEngine
import kotlin.system.measureTimeMillis

class LocalGameRunner {
    fun runGame(whiteEngine : IChessEngine, blackEngine : IChessEngine) {
        var board = Board.fromStandardPosition()

        fun play(engine : IChessEngine, side : Board.Side) : Boolean {
            if(!board.validMoves().any()) {
                println("${side} lost")
                return false
            }

            if(board.gameState() == Board.GameState.draw_by_threefold_repetition
                || board.gameState() == Board.GameState.draw_by_fifty_move_rule
                || board.gameState() == Board.GameState.stalemate ) {
                println("Draw / stalemate")
                return false
            }

            val time = measureTimeMillis {
                val move = engine.calculateMove(board)
                println("${side} plays: ${move}")
                board = board.play(move)
            }

            println("It took ${time}ms")
            println(board.toFEN())
            println(board)
            println()
            return true
        }

        while (true) {
            if(!play(whiteEngine, Board.Side.WHITE))
                return

            if(!play(blackEngine, Board.Side.BLACK))
                return
        }
    }
}