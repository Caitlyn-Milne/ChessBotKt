package cutelyn.Board

// 1 2 4
// 0 1 1 1 0 1 1 1
enum class PieceType(val byte: Byte){
    NONE(7),
    PAWN(1),
    KNIGHT(2),
    BISHOP(3),
    ROOK(4),
    QUEEN(5),
    KING(6),
    INVALID(0);

    val uByte
        get() = byte.toUByte()

    companion object {
        fun fromByte(byte: Byte): PieceType {
            return when(byte){
                NONE.byte -> NONE
                PAWN.byte -> PAWN
                KNIGHT.byte -> KNIGHT
                BISHOP.byte -> BISHOP
                ROOK.byte -> ROOK
                QUEEN.byte -> QUEEN
                KING.byte -> KING
                else -> INVALID
            }
        }
    }
}