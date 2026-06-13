package cutelyn.Util

import chariot.util.Board
import chariot.util.Board.PieceType


// TODO change source instead of patching it with an extension
fun Board.validMovesWithPromotions(): Set<Board.Move> {
    val rank = if (whiteToMove()) 7 else 0
    val validMoves = validMoves()
    val (promoMoves, normalMoves) = validMoves.partition { move ->
        move is Board.FromTo
                && this[move.from].type() == PieceType.PAWN
                && move.to.row() == rank
    }
    val promotions = promoMoves.filterIsInstance<Board.FromTo>().flatMap { move ->
        listOf(PieceType.QUEEN, PieceType.BISHOP, PieceType.ROOK, PieceType.KNIGHT).map { piece ->
            Board.Promotion(move, piece)
        }
    }
    return (normalMoves + promotions).toSet()
}