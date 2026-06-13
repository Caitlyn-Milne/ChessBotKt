package cutelyn.engines

import chariot.util.Board

interface IChessEngine {
    fun calculateMove(board : Board) : Board.Move

    fun calculateMoveForDebugging(board : Board, move : Board.Move) : Double
}