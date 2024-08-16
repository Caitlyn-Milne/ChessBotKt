package cutelyn
import chariot.Client
import chariot.util.Board
import cutelyn.engines.MultithreadedEngine
import cutelyn.engines.SingleThreadedEngine
import cutelyn.evaluators.AlphaBetaEvaluator
import cutelyn.evaluators.PointsEvaluatorWithTables
import cutelyn.evaluators.RandomEvaluator
import cutelyn.evaluators.SimpleDfsEvaluator
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun main() {
    val whiteEngine = MultithreadedEngine(AlphaBetaEvaluator(4, PointsEvaluatorWithTables()))
    val blackEngine = MultithreadedEngine(AlphaBetaEvaluator(3, PointsEvaluatorWithTables()))
    LocalGameRunner().runGame(whiteEngine, blackEngine)

    //val engine = MultithreadedEngine(AlphaBetaEvaluator(4, PointsEvaluatorWithTables()))
    //LichessAIGameRunner(engine, Client.auth(LICHESS_TOKEN)).runGame(4, Board.Side.WHITE)

     //runBlocking {
     //    LichessServer().waitForChallenge()
     //}
}




