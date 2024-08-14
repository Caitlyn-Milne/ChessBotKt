package cutelyn.evaluators

import chariot.util.Board
import chariot.util.Board.Piece
import chariot.util.Board.PieceType
import chariot.util.Board.Side
import java.util.Random

class PointsEvaluator : IEvaluator
{

    private val random = Random()

    override fun evaluate(board: Board): Double {
        val validMoves = board.validMoves()
        val side = if(board.whiteToMove())Side.WHITE else Side.BLACK
        var piecesDelta = 0.00
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                val piece = board[row, col] ?: continue
                val pieceScore = pieceToScore(piece, row, col)
                if (piece.color() == side)
                    piecesDelta += pieceScore
                else
                    piecesDelta -= pieceScore
            }
        }
        piecesDelta *= 100
        val rand = (random.nextDouble() - 0.50)
        return piecesDelta + rand + validMoves.size
    }


    private fun pieceToScore(piece: Piece, row : Int, col : Int): Double {
        return when (piece.type()) {
            PieceType.PAWN -> 1.00
            PieceType.KNIGHT -> 3.00
            PieceType.BISHOP -> 3.00
            PieceType.ROOK -> 5.00
            PieceType.QUEEN -> 9.00
            PieceType.KING -> 100.00
        }
    }
}