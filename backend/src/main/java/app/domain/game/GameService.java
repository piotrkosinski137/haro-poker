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

import app.web.rest.dto.UpdatePlayerBalanceRequest;
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

    public void startGame() {
        startRound();
        game.setGameTimeStamp(Instant.now().toEpochMilli());
        publisher.publishEvent(new GameChanged(this, getBlinds(), game.getGameTimeStamp()));
        publisher.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    public void startRound() {
        roundService.startRound(game.getActivePlayers(), game.getBlinds());
    }

    public Collection<GamePlayer> getPlayers() {
        return game.getGamePlayers();
    }

    public void changeActiveStatus(UUID id, boolean isActive) {
        game.changeActiveStatus(id, isActive);
    }

    public void finishRound(final UUID winnerPlayerId) {
        Deque<RoundPlayer> roundPlayers = roundService.finishRound(winnerPlayerId);
        roundPlayers.forEach(roundPlayer -> gamePlayerService.updateBalance(game.getActivePlayers(), roundPlayers));
        roundPlayers.forEach(RoundPlayer::clearBids);
        game.rotatePlayers();
        publisher.publishEvent(new GamePlayersChanged(this, getPlayers()));
        publisher.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    public void setEntryFee(final int entryFee) {
        game.setEntryFee(entryFee);
    }

    public void updateBlinds(int small) {
        game.updateBlinds(small);
        publisher.publishEvent(new GameChanged(this, getBlinds(), game.getGameTimeStamp()));
    }

    public Blinds getBlinds() {
        return game.getBlinds();
    }

    public long getTimeStamp() {
        return game.getGameTimeStamp();
    }

    public void removePlayer(UUID id) {
        game.removeGamePlayer(id);
        roundService.removeRoundPlayer(id);
        publisher.publishEvent(new GamePlayersChanged(this, game.getGamePlayers()));
        publisher.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    public void manualFinishRound(UpdatePlayerBalanceRequest updateBalances) {
        roundService.updatePlayersBalance(updateBalances);
        Collection<RoundPlayer> roundPlayers = roundService.getPlayers();
        roundPlayers.forEach(roundPlayer -> gamePlayerService.updateBalance(game.getActivePlayers(), roundPlayers));
        roundPlayers.forEach(RoundPlayer::clearBids);
        game.rotatePlayers();
        publisher.publishEvent(new GamePlayersChanged(this, getPlayers()));
        publisher.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    //finishGame (gameSummary)
}
