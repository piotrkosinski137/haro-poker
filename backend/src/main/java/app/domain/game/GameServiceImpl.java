package app.domain.game;

import app.domain.event.RoundPlayersChanged;
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
            ApplicationEventPublisher publisherGlobal, Game game) {
        this.roundService = roundService;
        this.gamePlayerService = gamePlayerService;
        this.publisher = publisher;
        this.publisherGlobal = publisherGlobal;
        this.game = game;
    }

    @Override
    public void startGame() {
        //todo restart players account
        startRound();
        game.setGameTimeStamp(Instant.now().toEpochMilli());
        publisher.publishGameEvent(game);
    }

    @Override
    public void startRound() {
        roundService.startRound(game.getActivePlayers(), game.getBlinds());
    }

    @Override
    public void finishRound(final UUID winnerPlayerId) { //todo move it in next stage
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

    @Override
    public void manualFinishRound(UpdatePlayerBalanceRequest updateBalances) { //todo move it in next stage
        roundService.updatePlayersBalance(updateBalances);
        Collection<RoundPlayer> roundPlayers = roundService.getPlayers();
        roundPlayers.forEach(roundPlayer -> gamePlayerService.updateBalance(game.getActivePlayers(), roundPlayers));
        roundPlayers.forEach(RoundPlayer::clearBids);
        game.rotatePlayers();
        publisher.publishGamePlayerEvent(game.getGamePlayers());
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers()));
    }

    @Override
    public void manualNextRound() { //todo move it in next stage
        roundService.manualNextRound();
    }

    @Override
    public GameDto getGame() {
        return new GameDto(game.getBlinds(), game.getGameTimeStamp());
    }

    //finishGame (gameSummary)
}
