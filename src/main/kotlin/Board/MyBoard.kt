package cutelyn.Board

class MyBoard {

    companion object {
        fun fromStandardPosition() : MyBoard {
            val board = MyBoard()

            for(i in 0 until 8) {
                board[i,1] = BoardSquare.create(PieceType.PAWN, true)
                board[i,6] = BoardSquare.create(PieceType.PAWN, false)
            }

            fun setBackRow(isWhite : Boolean) {
                val row = if(isWhite) 0 else 7
                board[0,row] = BoardSquare.create(PieceType.ROOK, isWhite)
                board[1,row] = BoardSquare.create(PieceType.KNIGHT, isWhite)
                board[2,row] = BoardSquare.create(PieceType.BISHOP, isWhite)
                board[4,row] = BoardSquare.create(PieceType.KING, isWhite)
                board[3,row] = BoardSquare.create(PieceType.QUEEN, isWhite)
                board[5,row] = BoardSquare.create(PieceType.BISHOP, isWhite)
                board[6,row] = BoardSquare.create(PieceType.KNIGHT, isWhite)
                board[7,row] = BoardSquare.create(PieceType.ROOK, isWhite)
            }

            setBackRow(true)
            setBackRow(false)
            return board
        }
    }

    private val squaresPacked = ByteArray(32) { BoardSquare.PACKED_EMPTY } // 48 bytes

    operator fun get(index : Int) : Byte {
        if(index !in 0 .. 63)
            throw IllegalArgumentException("Column and row must be between 0 and 63 inclusive.")
        val squaresPacked = squaresPacked[index / 2]
        return if(index % 2 == 0)
            BoardSquare.first(squaresPacked)
        else
            BoardSquare.second(squaresPacked)
    }

    operator fun get(col : Int, row : Int) : Byte {
        return get(BoardCoordinate.getIndex(col, row))
    }

    operator fun set(index : Int, value : Byte) {
        if(index !in 0 .. 63)
            throw IllegalArgumentException("Column and row must be between 0 and 63 inclusive.")
        val squarePacked = squaresPacked[index / 2]
        val first = if(index % 2 == 0) value else BoardSquare.first(squarePacked)
        val second = if(index % 2 == 1) value else BoardSquare.second(squarePacked)
        squaresPacked[index / 2] = BoardSquare.pack(first, second)
    }

    operator fun set(col : Int, row : Int, value : Byte) {
        set(BoardCoordinate.getIndex(col, row), value)
    }
}