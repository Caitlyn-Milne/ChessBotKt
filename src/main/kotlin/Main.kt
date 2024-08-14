package cutelyn
import chariot.Client
import chariot.ClientAuth
import chariot.model.*
import chariot.model.Enums.Color
import chariot.model.Event.ChallengeEvent
import chariot.model.Event.GameStartEvent
import chariot.model.GameStateEvent.Full
import chariot.model.GameStateEvent.State
import chariot.util.Board
import chariot.util.Board.Side
import cutelyn.engines.IChessEngine
import cutelyn.engines.MultithreadedDepthFirstSearchEngine
import cutelyn.evaluators.PointsEvaluatorWithTables
import kotlinx.coroutines.runBlocking


fun main() {
    //val whiteEngine = MultithreadedDepthFirstSearchEngine(PointsEvaluatorWithTables(), 3)
    //val blackEngine = MultithreadedDepthFirstSearchEngine(PointsEvaluatorWithTables(), 3)
    //runGameLocally(whiteEngine,blackEngine)
    //return
    //waitForChallenge()
    //val engine = MultithreadedDepthFirstSearchEngine(PointsEvaluatorWithTables(),4)
    //debugFEN("r1b1k1N1/1p1p1p1p/p3pBq1/8/Qp6/b4PN1/P1P3PP/R4RK1 b - - 8 23", engine)


    runBlocking {
        LichessServer().waitForChallenge()
    }
}




