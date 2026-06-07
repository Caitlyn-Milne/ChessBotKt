package cutelyn
import cutelyn.engines.NaiveIterativeDeepeningEngine
import cutelyn.evaluators.AlphaBetaEvaluator
import cutelyn.evaluators.PointsEvaluatorWithTables


fun main() {
    val whiteEngine = NaiveIterativeDeepeningEngine(AlphaBetaEvaluator(PointsEvaluatorWithTables()))
    val blackEngine = NaiveIterativeDeepeningEngine(AlphaBetaEvaluator(PointsEvaluatorWithTables()))
    LocalGameRunner().runGame(whiteEngine, blackEngine)

    //val engine = MultithreadedEngine(AlphaBetaEvaluator(4, PointsEvaluatorWithTables()))
    //LichessAIGameRunner(engine, Client.auth(LICHESS_TOKEN)).runGame(4, Board.Side.WHITE)

     //runBlocking {
     //    LichessServer().waitForChallenge()
     //}
}




