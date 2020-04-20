package app.domain.game;

import app.domain.round.RoundPlayer;
import java.util.Collection;
import java.util.UUID;

public interface GamePlayerService {

    UUID joinToGame(String playerName);

    void buyIn(UUID playerId);

    void changeActiveStatus(UUID id, boolean isActive);

    void removePlayer(UUID id);

    Collection<GamePlayerDto> getPlayers();

    void updateBalance(final Collection<GamePlayer> gamePlayers, final Collection<RoundPlayer> roundPlayers);

}
