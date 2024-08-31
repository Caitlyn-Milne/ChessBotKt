package  Board

import cutelyn.Board.BoardMove
import cutelyn.Board.PieceType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals
import kotlin.test.assertFails

class BoardMoveTests {
    @ParameterizedTest
    @CsvSource(
        "0000010000000000, 0000000000000001",
        "0000011111111111, 0000000000000001",
        "1111111111111111, 0000000000111111"
    )
    fun getFrom_shouldReturnFrom(moveBinary: String, expectedFromBinary: String) {
        val moveShort = moveBinary.toUShort(2)
        val expectedFromShort = expectedFromBinary.toUShort(2)
        assertEquals(expectedFromShort, BoardMove.getFrom(moveShort))
    }

    @ParameterizedTest
    @CsvSource(
        "0000001111010000, 0000000000111101",
        "1111110000011111, 0000000000000001",
        "1111111111111111, 0000000000111111"
    )
    fun getTo_shouldReturnTo(moveBinary: String, expectedToBinary: String) {
        val moveShort = moveBinary.toUShort(2)
        val expectedToShort = expectedToBinary.toUShort(2)
        assertEquals(expectedToShort, BoardMove.getTo(moveShort))
    }

    @ParameterizedTest
    @CsvSource(
        "1100001110001010, PROMOTION", //from 48 to 56, promoting queen
        "0000010010010000, STANDARD", //from 01 to 09
        "0001000001100001, CASTLING" //from 04 to 06, castling
    )
    fun getMoveType_shouldReturnMoveType(moveBinary: String, expectedMoveType: BoardMove.MoveType) {
        val moveShort = moveBinary.toUShort(2)
        assertEquals(expectedMoveType, BoardMove.getMoveType(moveShort))
    }

    @ParameterizedTest
    @CsvSource(
        "1100001110001010, QUEEN", //from 48 to 56, promoting queen
        "1100001110001000, ROOK", //from 48 to 56, promoting rook
        "1100001110000110, BISHOP", //from 48 to 56, promoting bishop
        "1100001110000100, KNIGHT", //from 48 to 56, promoting knight
    )
    fun getPromotionPieceType(moveBinary: String, expectedPieceType: PieceType) {
        val moveShort = moveBinary.toUShort(2)
        assertEquals(expectedPieceType, BoardMove.getPromotionPieceType(moveShort))
    }

    @ParameterizedTest
    @CsvSource(
        "48, 56, 1100001110000000",
        "01, 18, 0000010100100000"
    )
    fun createStandard_shouldCreateStandardMove(from : Int, to : Int, expectedMoveBinary : String) {
        val expectedMoveShort = expectedMoveBinary.toUShort(2)
        assertEquals(expectedMoveShort, BoardMove.createStandard(from, to))
    }

    @ParameterizedTest
    @CsvSource(
        "48, 56, QUEEN, 1100001110001010",
        "50, 58, ROOK, 1100101110101000",
        "52, 60, BISHOP, 1101001111000110",
        "55, 63, KNIGHT, 1101111111110100",
    )
    fun createPromotion_shouldCreatePromotionMove(from: Int, to : Int, pieceType: PieceType, expectedMoveBinary : String) {
        val expectedMoveShort = expectedMoveBinary.toUShort(2)
        assertEquals(expectedMoveShort, BoardMove.createPromotion(from, to, pieceType))
    }

    @ParameterizedTest
    @CsvSource(
        "04, 06, 0001000001100001",
        "04, 02, 0001000000100001",
        "60, 62, 1111001111100001",
        "60, 58, 1111001110100001",
    )
    fun createCastling_shouldCreateCastlingMove(from : Int, to : Int, expectedMoveBinary: String){
        val expectedMoveShort = expectedMoveBinary.toUShort(2)
        assertEquals(expectedMoveShort, BoardMove.createCastling(from, to))
    }

    @ParameterizedTest
    @CsvSource(
        "0, -1",
        "-1, 0",
        "64, 0",
        "0, 64"
    )
    fun createStandard_withInvalidValues_shouldFail(from : Int, to : Int) {
        assertFails {
            BoardMove.createStandard(from, to)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0, -1, QUEEN", // Out of bounds
        "-1, 0, QUEEN",
        "64, 0, QUEEN",
        "0, 64, QUEEN",
        "48, 56, KING", // Invalid Promotion Piece type
        "50, 58, INVALID",
        "51, 59, PAWN",
    )
    fun createPromotion_withInvalidValues_shouldFail(from : Int, to : Int, pieceType: PieceType) {
        assertFails {
            BoardMove.createPromotion(from, to, pieceType)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0, -1",
        "-1, 0",
        "64, 0",
        "0, 64"
    )
    fun createCastling_withInvalidValues_shouldFail(from : Int, to : Int) {
        assertFails {
            BoardMove.createCastling(from, to)
        }
    }
}