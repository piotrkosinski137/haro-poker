package app.domain.game;

import app.domain.game.exceptions.GameIsFull;
import app.domain.game.exceptions.GamePlayerNotFound;
import app.domain.round.RoundPlayer;
import java.util.Collection;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
class GamePlayerServiceImpl implements GamePlayerService {

    private final Game game;
    private final GameEventPublisher publisher;

    public GamePlayerServiceImpl(Game game, GameEventPublisher publisher) {
        this.game = game;
        this.publisher = publisher;
    }

    @Override
    public UUID joinToGame(String playerName) {
        if (game.isFull()) {
            throw new GameIsFull();
        }
        UUID playerId = game.addPlayer(createNewGamePlayer(playerName, game.findEmptyTableNumber()));
        publisher.publishGamePlayerEvent(getPlayers());
        return playerId;
    }

    @Override
    public void buyIn(UUID id) {
        findPlayerById(id).buyIn();
        publisher.publishGamePlayerEvent(getPlayers());
    }

    @Override
    public void removePlayer(UUID id) {
        game.getGamePlayers().remove(findPlayerById(id));
        publisher.publishGamePlayerEvent(getPlayers());
    }

    @Override
    public void changeActiveStatus(UUID id) {
        findPlayerById(id).changeState();
    }

    @Override
    public void updateAfterRound(final Collection<RoundPlayer> roundPlayers) {
        roundPlayers.forEach(roundPlayer -> game.getActivePlayers().stream()
                .filter(gamePlayer -> gamePlayer.getId().equals(roundPlayer.getId()))
                .findFirst()
                .ifPresent(playerToUpdate -> playerToUpdate.updateBalance(roundPlayer.getBalance())));
        game.rotatePlayers();
        publisher.publishGamePlayerEvent(getPlayers());
    }

    private GamePlayer createNewGamePlayer(final String playerName, int emptyTableNumber) {
        return new GamePlayer(playerName, emptyTableNumber);
    }

    private GamePlayer findPlayerById(UUID id) {
        return game.getGamePlayers().stream().filter(player -> player.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new GamePlayerNotFound(id));
    }

    @Override
    public Collection<GamePlayerDto> getPlayers() {
        return GamePlayerDto.fromGamePlayersCollection(game.getGamePlayers());
    }
}
