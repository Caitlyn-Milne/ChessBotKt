package cutelyn.evaluators

import chariot.util.Board
import chariot.util.Board.Piece
import chariot.util.Board.PieceType
import chariot.util.Board.Side
import java.util.Random

class PointsEvaluatorWithTables : IEvaluator
{
    private val pawnTable = arrayOf(
        doubleArrayOf(0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00),
        doubleArrayOf(50.00, 50.00, 50.00, 50.00, 50.00, 50.00, 50.00, 50.00),
        doubleArrayOf(10.00, 10.00, 20.00, 30.00, 30.00, 20.00, 10.00, 10.00),
        doubleArrayOf(5.00,  5.00, 10.00, 25.00, 25.00, 10.00,  5.00,  5.00),
        doubleArrayOf(0.00,  0.00,  0.00, 20.00, 20.00,  0.00,  0.00,  0.00),
        doubleArrayOf(5.00, -5.00,-10.00,  0.00,  0.00, -10.00, -5.00,  5.00),
        doubleArrayOf(5.00, 10.00, 10.00,-20.00,-20.00, 10.00, 10.00,  5.00),
        doubleArrayOf(0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00)
    )

    private val knightTable = arrayOf(
        doubleArrayOf(-50.00, -40.00, -30.00, -30.00, -30.00, -30.00, -40.00, -50.00),
        doubleArrayOf(-40.00, -20.00,   0.00,   0.00,   0.00,   0.00, -20.00, -40.00),
        doubleArrayOf(-30.00,   0.00,  10.00,  15.00,  15.00,  10.00,   0.00, -30.00),
        doubleArrayOf(-30.00,   5.00,  15.00,  20.00,  20.00,  15.00,   5.00, -30.00),
        doubleArrayOf(-30.00,   0.00,  15.00,  20.00,  20.00,  15.00,   0.00, -30.00),
        doubleArrayOf(-30.00,   5.00,  10.00,  15.00,  15.00,  10.00,   5.00, -30.00),
        doubleArrayOf(-40.00, -20.00,   0.00,   5.00,   5.00,   0.00, -20.00, -40.00),
        doubleArrayOf(-50.00, -40.00, -30.00, -30.00, -30.00, -30.00, -40.00, -50.00)
    )

    private val rookTable = arrayOf(
        doubleArrayOf(  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00),
        doubleArrayOf(  5.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00,  5.00),
        doubleArrayOf( -5.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00, -5.00),
        doubleArrayOf( -5.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00, -5.00),
        doubleArrayOf( -5.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00, -5.00),
        doubleArrayOf( -5.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00, -5.00),
        doubleArrayOf( -5.00,  0.00,  0.00,  0.00,  0.00,  0.00,  0.00, -5.00),
        doubleArrayOf(  0.00,  0.00,  0.00,  5.00,  5.00,  0.00,  0.00,  0.00)
    )

    private val bishopTable = arrayOf(
        doubleArrayOf(-20.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -20.00),
        doubleArrayOf(-10.00,   0.00,   0.00,   0.00,   0.00,   0.00,   0.00, -10.00),
        doubleArrayOf(-10.00,   0.00,   5.00,  10.00,  10.00,   5.00,   0.00, -10.00),
        doubleArrayOf(-10.00,   5.00,   5.00,  10.00,  10.00,   5.00,   5.00, -10.00),
        doubleArrayOf(-10.00,   0.00,  10.00,  10.00,  10.00,  10.00,   0.00, -10.00),
        doubleArrayOf(-10.00,  10.00,  10.00,  10.00,  10.00,  10.00,  10.00, -10.00),
        doubleArrayOf(-10.00,   5.00,   0.00,   0.00,   0.00,   0.00,   5.00, -10.00),
        doubleArrayOf(-20.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -20.00)
    )

    private val queenTable = arrayOf(
        doubleArrayOf(-20.00, -10.00, -10.00,  -5.00,  -5.00, -10.00, -10.00, -20.00),
        doubleArrayOf(-10.00,   0.00,   0.00,   0.00,   0.00,   0.00,   0.00, -10.00),
        doubleArrayOf(-10.00,   0.00,   5.00,   5.00,   5.00,   5.00,   0.00, -10.00),
        doubleArrayOf( -5.00,   0.00,   5.00,   5.00,   5.00,   5.00,   0.00,  -5.00),
        doubleArrayOf(  0.00,   0.00,   5.00,   5.00,   5.00,   5.00,   0.00,  -5.00),
        doubleArrayOf(-10.00,   5.00,   5.00,   5.00,   5.00,   5.00,   0.00, -10.00),
        doubleArrayOf(-10.00,   0.00,   5.00,   0.00,   0.00,   0.00,   0.00, -10.00),
        doubleArrayOf(-20.00, -10.00, -10.00,  -5.00,  -5.00, -10.00, -10.00, -20.00)
    )

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
        val weight = 300.00

        val r = if(piece.color() == Side.WHITE) 7 - row else row
        val c = if(piece.color() == Side.WHITE) 7 - col else col
        return when (piece.type()!!) {
            PieceType.PAWN -> 2.00 * (weight + pawnTable[r][c]) / weight
            PieceType.KNIGHT -> 3.00 * (weight + knightTable[r][c]) / weight
            PieceType.BISHOP -> 3.50 * (weight + bishopTable[r][c]) / weight
            PieceType.ROOK -> 5.00 * (weight + rookTable[r][c]) / weight
            PieceType.QUEEN -> 9.00 * (weight + queenTable[r][c]) / weight
            PieceType.KING -> 100.00
        }
    }
}