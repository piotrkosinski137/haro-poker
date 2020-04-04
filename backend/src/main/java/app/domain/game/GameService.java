package app.domain.game;

import app.domain.player.Player;
import app.domain.round.RoundPlayer;
import app.domain.round.RoundService;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

@Service
class GameService {

    private final RoundService roundService;
    private Game game;

    GameService(RoundService roundService) {
        this.roundService = roundService;
        game = new Game();
    }

    public void joinToGame(Player player) {
        game.addPlayer(player);
    }

    public void startRound() {
        game.movePlayers();
        roundService.startRound(convertToRoundPlayers(game.getActivePlayers()),
                game.getBlinds());
    }

    /*
     * Admin ends round and picks winner
     * Balances from roundPlayers are propagated to players
     */
    public void finishRound(int playerId) {

        //maybe it should return some kind of dto instead of round players
        Deque<RoundPlayer> roundPlayers = roundService.finishRound(playerId);
        roundPlayers.forEach(roundPlayer -> game.updateBalance(playerId, roundPlayer.getBalance()));
    }

    private Deque<RoundPlayer> convertToRoundPlayers(Deque<Player> players) {
        return players.stream()
                .map(player -> new RoundPlayer(player.getId(), player.getBalance()))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    //finishGame (gameSummary)

    //propagateRoundResults
}
