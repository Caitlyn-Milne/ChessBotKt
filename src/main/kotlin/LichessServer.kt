package cutelyn

import chariot.Client
import chariot.ClientAuth
import chariot.model.Enums
import chariot.model.Event.ChallengeCreatedEvent
import chariot.model.Event.GameStartEvent
import chariot.util.Board.Side
import cutelyn.engines.MultithreadedEngine
import cutelyn.evaluators.AlphaBetaEvaluator
import cutelyn.evaluators.PointsEvaluatorWithTables
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class LichessServer {

    val matchMutex : Mutex = Mutex()
    var numMatches = 0

    suspend fun waitForChallenge() {

        val client = Client.auth(LICHESS_TOKEN)
            ?: throw Exception("Could not connect")

        println("Waiting for challenge")


        for(event in client.bot().connect().stream()){
            println("event ${event}")
            when(event){
                is ChallengeCreatedEvent -> {
                    val challenge = event.challenge.id
                    matchMutex.withLock {
                        if(numMatches < 3) {
                            client.bot().acceptChallenge(challenge)
                        }
                        else {
                            client.bot().declineChallenge(challenge, Enums.DeclineReason.generic)
                        }
                    }
                }
                is GameStartEvent -> {
                    startGame(client, event)
                }
                else -> continue
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun startGame(client: ClientAuth, event: GameStartEvent) {
        matchMutex.lock {
            numMatches++
        }
        val engine = MultithreadedEngine(AlphaBetaEvaluator(4, PointsEvaluatorWithTables()))
        val runner = LichessGameRunner(client)
        val side = if (event.game.color == Enums.Color.white) Side.WHITE else Side.BLACK
        GlobalScope.async { //We can fire and forget, the runner will handle itself
            runner.connectAndRunGame(engine, event.gameId(), side)
            matchMutex.withLock {
                numMatches--
            }
        }
    }
}