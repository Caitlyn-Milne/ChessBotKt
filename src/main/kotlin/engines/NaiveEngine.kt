package cutelyn.engines

import chariot.util.Board
import chariot.util.Board.PieceType

class NaiveEngine(val side : Board.Side)  : IChessEngine {

    override fun calculateMove(board: Board): Board.Move {
        var bestMove : Board.Move = board.validMoves().first()
        var bestScore = Int.MIN_VALUE
        for(move in board.validMoves()) {
            val score = naiveEvaluate(board.play(move))
            if(score > bestScore){
                bestScore = score
                bestMove = move
            }
        }
        println("Best Move: ${bestMove}; Best Score: ${bestScore}")
        return bestMove
    }

    private fun naiveEvaluate(board : Board) : Int {
        var piecesDelta = 0
        for(row in 0 until 8){
            for (col in 0 until 8) {
                val piece = board[row,col] ?: continue
                val pieceScore = pieceTypeToScore(piece.type())
                if(piece.color() == side)
                    piecesDelta += pieceScore
                else
                    piecesDelta -= pieceScore
            }
        }

        return piecesDelta
    }

    private fun pieceTypeToScore(pieceType: PieceType) : Int {
        return when(pieceType) {
            PieceType.PAWN -> 1
            PieceType.KNIGHT -> 3
            PieceType.BISHOP -> 3
            PieceType.ROOK -> 5
            PieceType.QUEEN -> 9
            PieceType.KING -> 12
        }
    }
}