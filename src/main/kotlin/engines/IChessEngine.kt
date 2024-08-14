package cutelyn.engines

import chariot.util.Board

interface IChessEngine {
    fun calculateMove(board : Board) : Board.Move
}