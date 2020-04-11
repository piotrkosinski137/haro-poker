package app.domain.game;

import app.domain.event.GameChanged;
import app.domain.event.GamePlayersChanged;
import app.domain.event.RoundPlayersChanged;
import app.domain.game.exceptions.GameIsFull;
import app.domain.player.GamePlayer;
import app.domain.player.GamePlayerService;
import app.domain.round.RoundPlayer;
import app.domain.round.RoundService;
import java.time.Instant;
import java.util.Collection;
import java.util.Deque;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final RoundService roundService;
    private final GamePlayerService gamePlayerService;
    private final ApplicationEventPublisher publisher;
    private final Game game;

    public GameService(final RoundService roundService, final GamePlayerService gamePlayerService, ApplicationEventPublisher publisher) {
        this.roundService = roundService;
        this.gamePlayerService = gamePlayerService;
        this.publisher = publisher;
        game = new Game();
    }

    public UUID joinToGame(String playerName) {
        if (game.isFull()) {
            throw new GameIsFull();
        }
        UUID playerId = game.addPlayer(gamePlayerService.createNewGamePlayer(playerName, game.findEmptyTableNumber()));
        publisher.publishEvent(new GamePlayersChanged(this, getPlayers()));
        return playerId;
    }

    public Collection<GamePlayer> getPlayers() {
        return game.getGamePlayers();
    }

    public void startGame() {
        startRound();
        game.setGameTimeStamp(Instant.now().toEpochMilli());
        publisher.publishEvent(new GameChanged(this, getBlinds())); //TODO
        publisher.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    void startRound() {
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
        roundPlayers.forEach(roundPlayer -> gamePlayerService.updateBalance(game.getActivePlayers(), roundPlayers));
        game.rotatePlayers();
        publisher.publishEvent(new GamePlayersChanged(this, getPlayers()));
    }

    public void setEntryFee(final int entryFee) {
        game.setEntryFee(entryFee);
    }

    public void updateBlinds(int small) {
        game.updateBlinds(small);
        publisher.publishEvent(new GameChanged(this, getBlinds()));
    }

    public Blinds getBlinds() {   //TODO to remove
        return game.getBlinds();
    }

    //finishGame (gameSummary)

    //konczymy jedna runde a nie rozpoczynamy kolejnej w obecnym stanie ale moze to dobrze.
}
