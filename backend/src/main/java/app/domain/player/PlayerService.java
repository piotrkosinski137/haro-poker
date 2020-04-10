package app.domain.player;

import app.domain.round.RoundPlayer;
import java.util.Deque;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    public void updateBalance(final Deque<GamePlayer> gamePlayers, final Deque<RoundPlayer> roundPlayers) {
        roundPlayers.forEach(roundPlayer -> gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getId() == roundPlayer.getId())
                .findFirst()
                .ifPresent(playerToUpdate -> playerToUpdate.updateBalance(roundPlayer.getBalance())));
    }
}
