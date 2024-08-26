package cutelyn.Board

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or

object BoardSquare {
    val EMPTY : Byte = 0b00000111.toByte()
    val PACKED_EMPTY : Byte = 0b01110111.toByte()
    private val firstMask : Byte = 0b00001111.toByte()
    private val secondMask : Byte = 0b11110000.toByte()
    private val pieceMask : Byte = 0b00000111.toByte()
    private val whiteFlag : Byte = 0b00001000.toByte()

    /**
     * unpacks the piece type from the byte
     */
    fun getPieceType(byte: Byte): PieceType {
        if(byte and secondMask != 0.toByte())
            throw Exception("please unpack")
        return PieceType.fromByte(byte and pieceMask)
    }

    /**
     * sets the piece type
     */
    fun setPieceType(byte: Byte, pieceType: PieceType): Byte {
        if(byte and secondMask != 0.toByte())
            throw Exception("please unpack")
        return (byte.toInt() and pieceMask.inv().toInt() or (pieceType.byte.toInt() and pieceMask.toInt())).toByte()
    }

    /**
     * sets the white flag
     */
    fun setWhite(byte: Byte, white : Boolean): Byte {
        if (byte and secondMask != 0.toByte())
            throw Exception("please unpack")
        return if (white)
            byte or whiteFlag
        else
            byte and whiteFlag.inv()
    }

    /**
     * gets the first piece packed in the byte
     */
    fun first(byte: Byte): Byte {
        return byte and firstMask
    }

    /**
     * gets the second piece packed in the byte
     */
    fun second(byte: Byte): Byte {
        return ((byte and secondMask).toUByte().toInt() shr 4).toByte()
    }

    /**
     * unpacks the is white flag from the byte
     */
    fun isWhite(byte : Byte) : Boolean {
        if(byte and secondMask != 0.toByte())
            throw Exception("please unpack")
        return byte and whiteFlag == whiteFlag
    }

    /**
     * repacks the first and second pieces back into a single byte
     */
    fun pack(first : Byte, second : Byte) : Byte {
        return (first.toUInt() or (second.toUInt() shl 4)).toByte()
    }
}