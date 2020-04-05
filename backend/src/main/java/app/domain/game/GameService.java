package app.domain.game;

import app.domain.player.Player;
import app.domain.player.PlayerService;
import app.domain.round.RoundPlayer;
import app.domain.round.RoundService;
import java.util.Deque;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final RoundService roundService;
    private final PlayerService playerService;
    private Game game;
    //gameTime - it should starts with first round!

    public GameService(final RoundService roundService, final PlayerService playerService) {
        this.roundService = roundService;
        this.playerService = playerService;
        game = new Game();
    }

    public void joinToGame(Player player) {
        game.addPlayer(player);
    }

    public void startRound() {
        roundService.startRound(game.getActivePlayers(), game.getBlinds());
    }

    /**
     * Admin ends round and picks winner Balances from roundPlayers are propagated to players
     */
    public void finishRound(final int winnerPlayerId) {
        Deque<RoundPlayer> roundPlayers = roundService.finishRound(winnerPlayerId);
        roundPlayers.forEach(roundPlayer -> playerService.updateBalance(game.getActivePlayers(), roundPlayers));
        game.rotatePlayers();
    }

    public void setEntryFee(final int entryFee) {
       game.setEntryFee(entryFee);
    }

    public void setBlinds(final int blind) {
        game.getBlinds().setBlinds(blind);
    }

    //finishGame (gameSummary)

    //propagateRoundResults
}
