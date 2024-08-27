package Board

import cutelyn.Board.BoardSquare
import cutelyn.Board.MyBoard
import cutelyn.Board.PieceType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class MyBoardTests {
    @Test
    fun constructor_shouldBeEmpty() {
        val board = MyBoard()

        for(i in 0 until 64) {
            assertEquals(BoardSquare.EMPTY, board[i])
        }
    }

    @Test
    fun get_index_whenModified_shouldBeModified() {
        val board = MyBoard()

        val pawn = BoardSquare.setPieceType(BoardSquare.EMPTY, PieceType.PAWN)
        val knight = BoardSquare.setPieceType(BoardSquare.EMPTY, PieceType.KNIGHT)

        assertEquals(BoardSquare.EMPTY, board[0])
        board[0] = pawn
        assertEquals(pawn, board[0])
        assertEquals(BoardSquare.EMPTY, board[1])

        assertEquals(BoardSquare.EMPTY, board[21])
        board[21] = pawn
        assertEquals(pawn, board[21])

        assertEquals(BoardSquare.EMPTY, board[20])
        board[20] = knight
        assertEquals(knight, board[20])
        assertEquals(pawn, board[21])

        assertEquals(BoardSquare.EMPTY, board[63])
        board[63] = pawn
        assertEquals(pawn, board[63])
        assertEquals(BoardSquare.EMPTY, board[62])
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 64, 70])
    fun get_index_whenOutOfBounds_shouldThrow(index : Int) {
        val board = MyBoard()
        assertFails {
            board.get(index)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "-1, -1",
        "8, 8",
        "-1, 0",
        "0, -1",
        "8, 0",
        "0, 8",
    )
    fun get_rowCol_whenOutOfBounds_shouldThrow(row : Int, col : Int) {
        val board = MyBoard()
        assertFails {
            board.get(row, col)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 64, 70])
    fun set_index_whenOutOfBounds_shouldThrow(index : Int){
        val board = MyBoard()
        assertFails {
            board.set(index, 0b00001001.toByte())
        }
    }

    @ParameterizedTest
    @CsvSource(
        "-1, -1",
        "8, 8",
        "-1, 0",
        "0, -1",
        "8, 0",
        "0, 8",
    )
    fun set_rowCol_whenOutOfBounds_shouldThrow(row : Int, col : Int) {
        val board = MyBoard()
        assertFails {
            board.set(row, col, 0b00001001.toByte())
        }
    }

    @Test
    fun fromStandardPosition(){
        val board = MyBoard.fromStandardPosition()

        val empty = BoardSquare.EMPTY
        val whitePawn = BoardSquare.create(PieceType.PAWN, true)
        val whiteRook = BoardSquare.create(PieceType.ROOK, true)
        val whiteKnight = BoardSquare.create(PieceType.KNIGHT, true)
        val whiteBishop = BoardSquare.create(PieceType.BISHOP, true)
        val whiteQueen = BoardSquare.create(PieceType.QUEEN, true)
        val whiteKing = BoardSquare.create(PieceType.KING, true)

        val blackPawn = BoardSquare.create(PieceType.PAWN, false)
        val blackRook = BoardSquare.create(PieceType.ROOK, false)
        val blackKnight = BoardSquare.create(PieceType.KNIGHT, false)
        val blackBishop = BoardSquare.create(PieceType.BISHOP, false)
        val blackQueen = BoardSquare.create(PieceType.QUEEN, false)
        val blackKing = BoardSquare.create(PieceType.KING, false)

        val expectedBytes = byteArrayOf(
            whiteRook, whiteKnight, whiteBishop, whiteQueen, whiteKing, whiteBishop, whiteKnight, whiteRook,
            whitePawn, whitePawn, whitePawn, whitePawn, whitePawn, whitePawn, whitePawn, whitePawn,
            empty, empty, empty, empty, empty, empty, empty, empty,
            empty, empty, empty, empty, empty, empty, empty, empty,
            empty, empty, empty, empty, empty, empty, empty, empty,
            empty, empty, empty, empty, empty, empty, empty, empty,
            blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn, blackPawn,
            blackRook, blackKnight, blackBishop, blackQueen, blackKing, blackBishop, blackKnight, blackRook
        )

        val actualBytes = ByteArray(64) { i -> board[i] }
        Assertions.assertArrayEquals(expectedBytes, actualBytes)
    }
}