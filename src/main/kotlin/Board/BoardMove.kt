package cutelyn.Board

import cutelyn.shl
import cutelyn.shr

// first 6 bits: from
// second 6 bits: to
// next 3 bits: piece type
// last bit: is castling flag
object BoardMove {

    enum class MoveType {
        STANDARD,
        PROMOTION,
        CASTLING,
    }

    val FROM_MASK = 0b1111110000000000.toUShort()
    val TO_MASK = 0b0000001111110000.toUShort()
    val PIECE_TYPE_MASK = 0b0000000000001110.toUShort()
    val CASTLING_FLAG = 0b0000000000000001.toUShort()

    const val FROM_OFFSET = 10
    const val TO_OFFSET = 4
    const val PIECE_TYPE_OFFSET = 1
    const val CASTLING_FLAG_OFFSET = 0

    /**
     * gets from index from move
     */
    fun getFrom(move : UShort) : UShort {
        return (move and FROM_MASK) shr FROM_OFFSET
    }

    /**
     * gets to index from move
     */
    fun getTo(move : UShort) : UShort {
        return (move and TO_MASK) shr TO_OFFSET
    }

    /**
     * gets move type from move
     */
    fun getMoveType(move : UShort) : MoveType {
        if(move and PIECE_TYPE_MASK != 0.toUShort())
            return MoveType.PROMOTION
        if(move and CASTLING_FLAG != 0.toUShort())
            return MoveType.CASTLING
        return MoveType.STANDARD
    }

    /**
     * get promotion piece type from move
     */
    fun getPromotionPieceType(move : UShort) : PieceType {
        val byte = ((move and PIECE_TYPE_MASK) shr PIECE_TYPE_OFFSET).toByte()
        return PieceType.fromByte(byte)
    }

    /**
     * Creates standard from to move
     * Note: minimal validation logic
     * @throws IllegalArgumentException
     */
    fun createStandard(from : Int, to : Int) : UShort {
        if(from !in 0..63)
            throw IllegalArgumentException("From must be between 0 and 63 inclusive.")
        if(to !in 0..63)
            throw IllegalArgumentException("To must be between 0 and 63 inclusive.")
        var move = 0.toUShort()

        val fromShifted = (from shl FROM_OFFSET).toUShort()
        move = (move and FROM_MASK.inv()) or fromShifted

        val toShifted = (to shl TO_OFFSET).toUShort()
        return (move and TO_MASK.inv()) or toShifted
    }

    /**
     * Creates promotion move
     * Note: minimal validation logic
     * @throws IllegalArgumentException
     */
    fun createPromotion(from : Int, to : Int, pieceType: PieceType) : UShort {
        when (pieceType) {
            PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT -> {
                val move = createStandard(from, to)
                val pieceTypeShifted = (pieceType.uByte shl PIECE_TYPE_OFFSET).toUShort()
                return (move and PIECE_TYPE_MASK.inv()) or pieceTypeShifted
            }
            else -> throw IllegalArgumentException("Invalid piece type for promotion")
        }
    }

    /**
     * Creates castling move
     * Note: minimal validation logic
     * @throws IllegalArgumentException
     */
    fun createCastling(from : Int, to : Int) : UShort {
        return createStandard(from, to) or CASTLING_FLAG
    }
}
