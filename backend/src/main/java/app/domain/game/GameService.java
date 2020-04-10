package app.domain.game;

import app.domain.player.GamePlayer;
import app.domain.player.PlayerService;
import app.domain.round.RoundPlayer;
import app.domain.round.RoundService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Deque;
import java.util.UUID;

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

    public UUID joinToGame(String playerName) {
        if (game.isFull()) {
            throw new GameIsFull();
        }
        return game.addPlayer(playerName);
    }

    public Collection<GamePlayer> getPlayers() {
        return game.getGamePlayers();
    }

    public void startGame() {
        game.activatePlayers();
        startRound();
    }

    public void startRound() {
        roundService.startRound(game.getActivePlayers(), game.getBlinds());
    }

    public void changeActiveStatus(UUID id, boolean isActive) {
        game.changeActiveStatus(id, isActive);
    }

    /**
     * Admin ends round and picks winner Balances from roundPlayers are propagated to players
     */
    public void finishRound(final UUID winnerPlayerId) {
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

    //konczymy jedna runde a nie rozpoczynamy kolejnej w obecnym stanie ale moze to dobrze.
}
