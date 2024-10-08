package cutelyn.engines

import chariot.util.Board
import cutelyn.evaluators.IBoardEvaluator

class SingleThreadedEngine(val evaluator: IBoardEvaluator) : IChessEngine {
    override fun calculateMove(board : Board): Board.Move {
        var bestMove : Board.Move = board.validMoves().first()
        var bestScore = -1000000.00

        for(move in board.validMoves()) {
            val boardAfterMove = board.play(move)
            val score = -evaluator.evaluate(boardAfterMove, boardAfterMove.validMoves())
            if(score > bestScore){
                bestScore = score
                bestMove = move
            }
        }
        println("Best Move: ${bestMove} Best score: ${bestScore}")
        return bestMove
    }
}