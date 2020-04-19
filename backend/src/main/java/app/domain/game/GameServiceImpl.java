package app.domain.game;

import app.domain.event.RoundPlayersChanged;
import app.domain.game.exceptions.GameIsFull;
import app.domain.player.GamePlayer;
import app.domain.player.GamePlayerService;
import app.domain.round.RoundPlayer;
import app.domain.round.RoundService;
import app.web.rest.dto.UpdatePlayerBalanceRequest;
import java.time.Instant;
import java.util.Collection;
import java.util.Deque;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
class GameServiceImpl implements GameService {

    private final RoundService roundService;
    private final GamePlayerService gamePlayerService;
    private final GameEventPublisher publisher;
    private final ApplicationEventPublisher publisherGlobal; //todo remove in next step refactor
    private final Game game;

    public GameServiceImpl(final RoundService roundService, final GamePlayerService gamePlayerService, GameEventPublisher publisher,
            ApplicationEventPublisher publisherGlobal) {
        this.roundService = roundService;
        this.gamePlayerService = gamePlayerService;
        this.publisher = publisher;
        this.publisherGlobal = publisherGlobal;
        game = new Game();
    }

    public UUID joinToGame(String playerName) {
        if (game.isFull()) {
            throw new GameIsFull();
        }
        UUID playerId = game.addPlayer(gamePlayerService.createNewGamePlayer(playerName, game.findEmptyTableNumber()));
        publisher.publishGamePlayerEvent(game.getGamePlayers());
        return playerId;
    }

    @Override
    public void startGame() {
        startRound();
        game.setGameTimeStamp(Instant.now().toEpochMilli());
        publisher.publishGameEvent(game);
    }

    @Override
    public void startRound() {
        roundService.startRound(game.getActivePlayers(), game.getBlinds());
    }

    public void changeActiveStatus(UUID id, boolean isActive) {
        game.changeActiveStatus(id, isActive);
    }

    @Override
    public void finishRound(final UUID winnerPlayerId) {
        Deque<RoundPlayer> roundPlayers = roundService.finishRound(winnerPlayerId);
        roundPlayers.forEach(roundPlayer -> gamePlayerService.updateBalance(game.getActivePlayers(), roundPlayers));
        roundPlayers.forEach(RoundPlayer::clearBids);
        game.rotatePlayers();
        publisher.publishGamePlayerEvent(game.getGamePlayers());
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    @Override
    public void setEntryFee(final int entryFee) {
        game.setEntryFee(entryFee);
    }

    @Override
    public void updateBlinds(int small) {
        game.updateBlinds(small);
        publisher.publishGameEvent(game);
    }

    public void removePlayer(UUID id) {
        game.removeGamePlayer(id);
        roundService.removeRoundPlayer(id);
        publisher.publishGamePlayerEvent(game.getGamePlayers());
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    @Override
    public void manualFinishRound(UpdatePlayerBalanceRequest updateBalances) {
        roundService.updatePlayersBalance(updateBalances);
        Collection<RoundPlayer> roundPlayers = roundService.getPlayers();
        roundPlayers.forEach(roundPlayer -> gamePlayerService.updateBalance(game.getActivePlayers(), roundPlayers));
        roundPlayers.forEach(RoundPlayer::clearBids);
        game.rotatePlayers();
        publisher.publishGamePlayerEvent(game.getGamePlayers());
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    public void buyIn(UUID playerId) {
        game.buyIn(playerId);
        publisher.publishGamePlayerEvent(game.getGamePlayers());
    }

    @Override
    public void manualNextRound() {
        roundService.manualNextRound();
    }

    @Override
    public GameDto getGameDto() {
        return new GameDto(game.getBlinds(), game.getGameTimeStamp());
    }

    @Override
    public Collection<GamePlayer> getPlayers() {
        return game.getGamePlayers(); //todo remove in next refactor iteration
    }

    //finishGame (gameSummary)
}
