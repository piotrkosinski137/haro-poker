package app.domain.player;

import app.domain.round.RoundPlayer;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GamePlayerService {

    public void updateBalance(final Collection<GamePlayer> gamePlayers, final Collection<RoundPlayer> roundPlayers) {
        roundPlayers.forEach(roundPlayer -> gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getId().equals(roundPlayer.getId()))
                .findFirst()
                .ifPresent(playerToUpdate -> playerToUpdate.updateBalance(roundPlayer.getBalance())));
    }

    public GamePlayer createNewGamePlayer(final String playerName, int emptyTableNumber) {
        return new GamePlayer(playerName, emptyTableNumber);
    }
}
