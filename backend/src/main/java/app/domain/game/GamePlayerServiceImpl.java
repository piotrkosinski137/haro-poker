package app.domain.game;

import app.domain.event.RoundPlayersChanged;
import app.domain.game.exceptions.GameIsFull;
import app.domain.round.RoundPlayer;
import app.domain.round.RoundService;
import java.util.Collection;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
class GamePlayerServiceImpl implements GamePlayerService{

    private final Game game;
    private final RoundService roundService;
    private final GameEventPublisher publisher;
    private final ApplicationEventPublisher publisherGlobal; //todo remove in next step refactor

    public GamePlayerServiceImpl(Game game, RoundService roundService, GameEventPublisher publisher, ApplicationEventPublisher publisherGlobal) {
        this.game = game;
        this.roundService = roundService;
        this.publisher = publisher;
        this.publisherGlobal = publisherGlobal;
    }

    @Override
    public UUID joinToGame(String playerName) {
        if (game.isFull()) {
            throw new GameIsFull();
        }
        UUID playerId = game.addPlayer(createNewGamePlayer(playerName, game.findEmptyTableNumber()));
        publisher.publishGamePlayerEvent(game.getGamePlayers());
        return playerId;
    }

    @Override
    public void buyIn(UUID playerId) {
        game.buyIn(playerId);
        publisher.publishGamePlayerEvent(game.getGamePlayers());
    }

    @Override
    public void removePlayer(UUID id) {
        game.removeGamePlayer(id);
        roundService.removeRoundPlayer(id);
        publisher.publishGamePlayerEvent(game.getGamePlayers());
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, roundService.getPlayers())); //todo move it in next stage
    }

    @Override
    public void changeActiveStatus(UUID id, boolean isActive) {
        game.changeActiveStatus(id, isActive);
    }

    @Override
    public void updateBalance(final Collection<GamePlayer> gamePlayers, final Collection<RoundPlayer> roundPlayers) {
        roundPlayers.forEach(roundPlayer -> gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getId().equals(roundPlayer.getId()))
                .findFirst()
                .ifPresent(playerToUpdate -> playerToUpdate.updateBalance(roundPlayer.getBalance())));
    }

    GamePlayer createNewGamePlayer(final String playerName, int emptyTableNumber) {
        return new GamePlayer(playerName, emptyTableNumber);
    }

    @Override
    public Collection<GamePlayerDto> getPlayers() {
        return GamePlayerDto.fromGamePlayersCollection(game.getGamePlayers());
    }
}
