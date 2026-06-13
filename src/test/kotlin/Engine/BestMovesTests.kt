package Engine

import chariot.util.Board
import chariot.util.Board.FEN
import cutelyn.engines.IChessEngine
import cutelyn.engines.MultithreadedEngine
import cutelyn.evaluators.AlphaBetaEvaluator
import cutelyn.evaluators.PointsEvaluator
import cutelyn.evaluators.PointsEvaluatorWithTables
import cutelyn.evaluators.SimpleDfsEvaluator
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.file.Files.move
import kotlin.coroutines.CoroutineContext
import kotlin.test.assertEquals

class BestMovesTests {
    val engine = MultithreadedEngine(AlphaBetaEvaluator(PointsEvaluator()))
    @ParameterizedTest
    @CsvSource(
        "2k5/ppp2pp1/2b4p/8/5P2/4r3/N1r5/6K1 b - - 0 33, e3e1",
        "rnbqkbnr/ppppp2p/8/5Pp1/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 1, d1h5",
        "8/7k/P7/8/8/8/7K/8 w, a6a7", // Promote Pawn 2 moves away
        "8/7k/8/P7/8/8/7K/8 w, a5a6", // Promote Pawn 3 moves away
        "8/P7/7K/8/8/7k/8/8 w - - 0 1, a7a8q" // Promote Pawn 1 move away
    )
    fun assertBestMove(fen: String, expectedMove : String) {
        val board = Board.fromFEN(fen)
        val engine = engine

        println("Best move eval:" + engine.calculateMoveForDebugging(board,
            Board.Move.parse(expectedMove, fen)))

        val actualMove = engine.calculateMove(board).toString()
        assertEquals(expectedMove, actualMove)
    }
}