package cutelyn

import chariot.ClientAuth
import chariot.model.Enums
import chariot.model.Fail
import chariot.model.GameStateEvent
import chariot.model.GameStateEvent.Full
import chariot.model.GameStateEvent.State
import chariot.util.Board
import cutelyn.engines.IChessEngine

class Box<T>(var value : T) {
    fun get(): T {
        return value
    }
    fun set(newValue: T) {
        value = newValue
    }
}

open class LichessGameRunner(var client : ClientAuth) {
    fun connectAndRunGame(engine: IChessEngine, gameId : String, side : Board.Side) {
        val events = client.bot().connectToGame(gameId)
        if (events is Fail<*>)
            throw Exception("Could not connect to the game")

        var board = Box(Board.fromStandardPosition())

        //TODO cleanup this check side logic
        val gameInfo = client.games().byGameId(gameId).get()
        val name = client.account().profile().get().name()
        val isWhite = gameInfo.players.white.name() == name

        for(event in events.stream()) {
            if(!handleEvent(event, isWhite, board, engine, gameId)) return
        }
    }

    private fun handleEvent(
        event: GameStateEvent,
        isWhite: Boolean,
        board : Box<Board>,
        engine: IChessEngine,
        gameId: String
    ) : Boolean {
        when (event) {
            is Full -> {
                if (isWhite)
                    playTurn(true, board.get(), engine, gameId)
            }
            is State -> {
                println("EVENT: ${event.status}")
                when (event.status) {
                    Enums.Status.started -> {
                        val lastMove = event.moveList().last()
                        board.set(board.get().play(lastMove))
                        playTurn(isWhite, board.get(), engine, gameId)
                    }
                    else -> {
                        return false
                    }
                }

            }
            else -> {
                println("Unhandled event: ${event.type()}")
            }
        }
        return true
    }

    private fun playTurn(
        isWhite: Boolean,
        board: Board,
        engine: IChessEngine,
        gameId: String
    ) {
        if (board.validMoves().none()) return
        if (isWhite != board.whiteToMove()) return
        client.bot().chat(gameId, "My turn")
        val move = engine.calculateMove(board)
        client.bot().move(gameId, move.toString()) //TODO error check
    }
}