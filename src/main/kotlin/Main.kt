package cutelyn
import cutelyn.engines.MultithreadedEngine
import cutelyn.engines.SingleThreadedEngine
import cutelyn.evaluators.PointsEvaluatorWithTables
import cutelyn.evaluators.RandomEvaluator
import cutelyn.evaluators.SimpleDfsEvaluator


fun main() {
    val whiteEngine = SingleThreadedEngine(RandomEvaluator())
    val blackEngine = MultithreadedEngine(SimpleDfsEvaluator(3, PointsEvaluatorWithTables()))
    LocalGameRunner().runGame(whiteEngine, blackEngine)

    //runBlocking {
    //    LichessServer().waitForChallenge()
    //}
}




