package app.domain.game;

import app.domain.round.RoundPlayer;
import java.util.Collection;
import java.util.UUID;

public interface GamePlayerService {

    UUID joinToGame(String playerName);

    void buyIn(UUID playerId);

    void changeActiveStatus(UUID id);

    void removePlayer(UUID id);

    Collection<GamePlayerDto> getPlayers();

    void updateAfterRound(final Collection<RoundPlayer> roundPlayers);

}
