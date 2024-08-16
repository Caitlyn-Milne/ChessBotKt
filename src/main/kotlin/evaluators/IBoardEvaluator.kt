package cutelyn.evaluators

import chariot.util.Board

// Evaluators return a score for a given board state, from the perspective of who's turn it is
interface IBoardEvaluator {
    fun evaluate(board : Board, moves : Set<Board.Move>) : Double
}