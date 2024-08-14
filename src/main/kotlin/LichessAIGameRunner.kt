package cutelyn

import chariot.ClientAuth
import chariot.model.ChallengeAI
import chariot.model.Fail
import chariot.model.One
import chariot.util.Board.Side
import cutelyn.engines.IChessEngine

class LichessAIGameRunner(val engine : IChessEngine, client : ClientAuth) : LichessGameRunner(client) {

    fun runGame(level : Int, side: Side){
        val gameId = createGame()
        connectAndRunGame(engine, gameId, side)
    }

    private fun createGame() : String {
        val oneChallengeAi : One<ChallengeAI> = client.bot().challengeAI { conf ->
            conf.clockClassical30m0s()
                .color { it.black() }
                .level(4)
        } ?: throw Exception("Could not challenge bot")

        if (oneChallengeAi is Fail){
            throw Exception("Could not challenge bot ${oneChallengeAi.message()}")
        }

        val challengeAi = oneChallengeAi.get()

        println("Game started with id: ${challengeAi.id}")
        return challengeAi.id
    }
}