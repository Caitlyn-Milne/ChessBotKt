package Engine

import chariot.util.Board
import chariot.util.Board.FEN
import cutelyn.engines.MultithreadedEngine
import cutelyn.evaluators.AlphaBetaEvaluator
import cutelyn.evaluators.PointsEvaluatorWithTables
import cutelyn.evaluators.SimpleDfsEvaluator
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class BestMovesTests {

    @ParameterizedTest
    @CsvSource(
        "2k5/ppp2pp1/2b4p/8/5P2/4r3/N1r5/6K1 b - - 0 33, e3e1",
        "rnbqkbnr/ppppp2p/8/5Pp1/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, d1h5"
    )
    fun assertBestMove(fen: String, expectedMove : String) {
        val board = Board.fromFEN(fen)
        val engine = MultithreadedEngine(AlphaBetaEvaluator(4, PointsEvaluatorWithTables()))

        val actualMove = engine.calculateMove(board).toString()
        assertEquals(expectedMove, actualMove)
    }
}