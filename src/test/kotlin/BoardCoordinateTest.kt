import cutelyn.Board.BoardCoordinate
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFails

class BoardCoordinateTest {

    @ParameterizedTest
    @ValueSource(ints = [-1, 64, 70])
    fun getCol_whenOutOfBounds_shouldThrow(index : Int) {
        assertFails {
            BoardCoordinate.getCol(index)
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [-1, 64, 70])
    fun getRow_whenOutOfBounds_shouldThrow(index : Int) {
        assertFails {
            BoardCoordinate.getRow(index)
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
    fun getIndex_whenOutOfBounds_shouldThrow(column : Int, row : Int) {
        assertFails {
            BoardCoordinate.getIndex(column, row)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "0, 0",
        "1, 1",
        "2, 2",
        "3, 3",
        "4, 4",
        "5, 5",
        "6, 6",
        "7, 7",
        "8, 0",
        "32, 0",
        "33, 1",
        "34, 2",
        "35, 3",
        "36, 4",
        "37, 5",
        "38, 6",
        "39, 7",
        "63, 7"
    )
    fun getCol_shouldReturnCol(index : Int, expectedCol : Int) {
        val actualCol = BoardCoordinate.getCol(index)
        assertEquals(expectedCol, actualCol)
    }


    @ParameterizedTest
    @CsvSource(
        "0, 0",
        "8, 1",
        "16, 2",
        "24, 3",
        "32, 4",
        "40, 5",
        "48, 6",
        "56, 7",
        "4, 0",
        "12, 1",
        "20, 2",
        "28, 3",
        "36, 4",
        "44, 5",
        "52, 6",
        "60, 7",
        "63, 7"
    )
    fun getRow_shouldReturnRow(index : Int, expectedRow : Int) {
        val actualRow = BoardCoordinate.getRow(index)
        assertEquals(expectedRow, actualRow)
    }
}