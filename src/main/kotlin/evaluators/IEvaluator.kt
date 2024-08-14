package cutelyn.evaluators

import chariot.util.Board

interface IEvaluator {
    fun evaluate(board : Board) : Double
}