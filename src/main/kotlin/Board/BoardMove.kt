package cutelyn.Board

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

    val fromMask = 0b1111110000000000.toShort()
    val toMask = 0b0000001111110000.toShort()
    val pieceType = 0b0000000000001110.toShort()
    val castlingFlag = 0b0000000000000001.toShort()

    fun getFrom() : Short {
        throw NotImplementedError()
    }

    fun getTo() : Short {
        throw NotImplementedError()
    }

    fun getMoveType(move : Short) : MoveType {
        throw NotImplementedError()
    }

    fun getPromotionPieceType() : PieceType {
        throw NotImplementedError()
    }

    fun createFromTo(from : Int, to : Int) : Short {
        throw NotImplementedError()
    }

    fun createPromotion(from : Int, to : Int, pieceType: PieceType) : Short {
        throw NotImplementedError()
    }

    fun createCastlingFromTo(from : Int, to : Int) : Short {
        throw NotImplementedError()
    }
}
