package app.domain.game;

import app.domain.player.Player;
import app.domain.round.RoundService;

class GameService {

    private final RoundService roundService;
    private Game game;

    GameService(RoundService roundService) {
        this.roundService = roundService;
        game = new Game();
    }

    public void joinToGame(Player player) {

    }

    public void startRound() {
        roundService.startRound(game.getActivePlayers());
    }

    public void nextRound() {

    }

    //finishGame (gameSummary)

    //propagateRoundResults

}
