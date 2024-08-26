package Board

import cutelyn.Board.BoardSquare
import cutelyn.Board.PieceType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFails

class BoardSquareTests {

    @ParameterizedTest
    @ValueSource(ints = [0b11110000, 0b11111111])
    fun getPieceType_whenPacked_shouldThrow(input : Int) {
        val inputByte = input.toByte()
        assertFails {
            BoardSquare.getPieceType(inputByte)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [0b11110000, 0b11111000])
    fun isWhite_whenPacked_shouldThrow(input : Int) {
        val inputByte = input.toByte()
        assertFails {
            BoardSquare.isWhite(inputByte)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "11110000, true",
        "11111000, false",
    )
    fun setWhite_whenPacked_shouldThrow(value : String, white : Boolean) {
        val byteValue = value.toInt(2).toByte()
        assertFails {
            BoardSquare.setWhite(byteValue, white)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "11110000, PAWN",
        "11110000, KNIGHT",
        "11110000, BISHOP",
        "11110000, ROOK",
        "11110000, QUEEN",
        "11110000, KING",
        "11110000, NONE",
    )
    fun setPieceType_whenPacked_shouldThrow(value : String, pieceType: PieceType) {
        val byteValue = value.toInt(2).toByte()
        assertFails {
            BoardSquare.setPieceType(byteValue, pieceType)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "00000001, PAWN",
        "00000010, KNIGHT",
        "00000011, BISHOP",
        "00000100, ROOK",
        "00000101, QUEEN",
        "00000110, KING",
        "00000111, NONE",
        "00001001, PAWN",
        "00001010, KNIGHT",
        "00001011, BISHOP",
        "00001100, ROOK",
        "00001101, QUEEN",
        "00001110, KING",
        "00001111, NONE",
    )
    fun getPieceType_whenUnpacked_getsCorrectPieceType(value: String, expectedPieceType: PieceType) {
        val byteValue = value.toInt(2).toByte()
        val pieceType = BoardSquare.getPieceType(byteValue)
        assertEquals(expectedPieceType, pieceType)
    }

    @ParameterizedTest
    @CsvSource(
        "00000001, false",
        "00000010, false",
        "00000011, false",
        "00000100, false",
        "00000101, false",
        "00000110, false",
        "00000111, false",
        "00001001, true",
        "00001010, true",
        "00001011, true",
        "00001100, true",
        "00001101, true",
        "00001110, true",
        "00001111, true",
    )
    fun isWhite_whenUnpacked_getsCorrectColor(value: String, expectedColor : Boolean) {
        val byteValue = value.toInt(2).toByte()
        val isWhite = BoardSquare.isWhite(byteValue)
        assertEquals(expectedColor, isWhite)
    }

    @ParameterizedTest
    @CsvSource(
        "11110001, 00000001",
        "00000001, 00000001",
        "10101010, 00001010",
    )
    fun first_shouldUnpack(value : String, expectedUnpackedValue : String) {
        val byteValue = value.toInt(2).toByte()
        val byteExpectedUnpackedValue = expectedUnpackedValue.toInt(2).toByte()
        val unpackedValue = BoardSquare.first(byteValue)
        assertEquals(byteExpectedUnpackedValue, unpackedValue)
    }

    @ParameterizedTest
    @CsvSource(
        "00011111, 00000001",
        "00010000, 00000001",
        "11111111, 00001111",
        "10011111, 00001001",
    )
    fun second_shouldUnpack(value : String, expectedUnpackedValue : String) {
        val byteValue = value.toInt(2).toByte()
        val byteExpectedUnpackedValue = expectedUnpackedValue.toInt(2).toByte()
        val unpackedValue = BoardSquare.second(byteValue)
        assertEquals(byteExpectedUnpackedValue, unpackedValue)
    }

    @ParameterizedTest
    @CsvSource(
        "00001111, 00000001, 00011111",
        "00000000, 00000000, 00000000",
        "00001111, 00001111, 111111111",
        "00001111, 00000000, 00001111",
        "00000000, 00001111, 11110000",
    )
    fun pack_shouldPack(first : String, second : String, expectedPacked : String) {
        val byteFirst = first.toInt(2).toByte()
        val byteSecond = second.toInt(2).toByte()
        val byteExpectedPacked = expectedPacked.toInt(2).toByte()
        val packedValue = BoardSquare.pack(byteFirst, byteSecond)
        assertEquals(byteExpectedPacked, packedValue)
    }

    @ParameterizedTest
    @CsvSource(
        "00000111, PAWN, 00000001",
        "00000111, KNIGHT, 00000010",
        "00000111, BISHOP, 00000011",
        "00000111, ROOK, 00000100",
        "00000111, QUEEN, 00000101",
        "00000111, KING, 00000110",
        "00000000, NONE, 00000111",
        "00001111, PAWN, 00001001",
        "00001111, KNIGHT, 00001010",
        "00001111, BISHOP, 00001011",
        "00001111, ROOK, 00001100",
        "00001111, QUEEN, 00001101",
        "00001111, KING, 00001110",
    )
    fun setPieceType_shouldCorrectlyChangePieceType(value : String, pieceType : PieceType, expected : String) {
        val byteValue = value.toInt(2).toByte()
        val byteExpected = expected.toInt(2).toByte()
        val byteActual = BoardSquare.setPieceType(byteValue, pieceType)
        assertEquals(byteExpected, byteActual)
    }

    @ParameterizedTest
    @CsvSource(
        "00000000, true, 00001000",
        "00001000, false, 00000000",
        "00000111, true, 00001111",
        "00001111, false, 00000111",
    )
    fun setWhite_shouldChangeColor(value : String, white : Boolean, expected: String) {
        val byteValue = value.toInt(2).toByte()
        val byteExpected = expected.toInt(2).toByte()
        val byteActual = BoardSquare.setWhite(byteValue, white)
        assertEquals(byteExpected, byteActual)
    }
}
