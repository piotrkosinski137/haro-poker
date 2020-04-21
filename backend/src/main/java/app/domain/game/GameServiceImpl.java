package app.domain.game;

import app.domain.round.RoundPlayerServiceImpl;
import java.time.Instant;
import java.util.ArrayDeque;
import org.springframework.stereotype.Service;

@Service
class GameServiceImpl implements GameService {

    private final RoundPlayerServiceImpl roundPlayerService;
    private final GameEventPublisher publisher;
    private final Game game;

    public GameServiceImpl(final RoundPlayerServiceImpl roundPlayerService, GameEventPublisher publisher, Game game) {
        this.roundPlayerService = roundPlayerService;
        this.publisher = publisher;
        this.game = game;
    }

    @Override
    public void startGame() {
        //todo restart players account
        startRound();
        game.setGameTimeStamp(Instant.now().toEpochMilli());
        publisher.publishGameEvent(getGame());
    }

    @Override
    public void startRound() {
        roundPlayerService.startRound(new ArrayDeque<>(GamePlayerDto.fromGamePlayersCollection(game.getActivePlayers())), game.getBlinds());
    }

    @Override
    public void setEntryFee(final int entryFee) {
        game.setEntryFee(entryFee);
    }

    @Override
    public void updateBlinds(int small) {
        game.updateBlinds(small);
        publisher.publishGameEvent(getGame());
    }

    @Override
    public GameDto getGame() {
        return new GameDto(game.getBlinds(), game.getGameTimeStamp());
    }

    //finishGame (gameSummary)
}
