package cutelyn.Board

object BoardCoordinate {

    // Indices:
    // 56 57 58 59 60 61 62 63   7
    // 48 49 50 51 52 53 54 55   6
    // 40 41 42 43 44 45 46 47   5
    // 32 33 34 35 36 37 38 39   4
    // 24 25 26 27 28 29 30 31   3
    // 16 17 18 19 20 21 22 23   2
    // 08 09 10 11 12 13 14 15   1
    // 00 01 02 03 04 05 06 07   0
    //
    //  0  1  2  3  4  5  6  7

    // (Column, Row):
    //(0,7) (1,7) (2,7) (3,7) (4,7) (5,7) (6,7) (7,7)
    //(0,6) (1,6) (2,6) (3,6) (4,6) (5,6) (6,6) (7,6)
    //(0,5) (1,5) (2,5) (3,5) (4,5) (5,5) (6,5) (7,5)
    //(0,4) (1,4) (2,4) (3,4) (4,4) (5,4) (6,4) (7,4)
    //(0,3) (1,3) (2,3) (3,3) (4,3) (5,3) (6,3) (7,3)
    //(0,2) (1,2) (2,2) (3,2) (4,2) (5,2) (6,2) (7,2)
    //(0,1) (1,1) (2,1) (3,1) (4,1) (5,1) (6,1) (7,1)
    //(0,0) (1,0) (2,0) (3,0) (4,0) (5,0) (6,0) (7,0)

    /**
     * returns the column from a given index
     */
    fun getCol(index : Int) : Int {
        if(index !in 0 .. 63)
            throw IllegalArgumentException("Index must be between 0 and 63 inclusive.")
        return index % 8
    }

    /**
     * returns the row from a given index
     */
    fun getRow(index : Int) : Int {
        if(index !in 0 .. 63)
            throw IllegalArgumentException("Index must be between 0 and 63 inclusive.")
        return index / 8
    }

    /**
     * Returns the index for a given column and row.
     */
    fun getIndex(col: Int, row: Int): Int {
        if (col !in 0..7 || row !in 0..7)
            throw IllegalArgumentException("Column and row must be between 0 and 7 inclusive.")
        return row * 8 + col
    }
}