package app.domain.round;

import app.domain.game.Blinds;
import app.domain.player.Player;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.stream.Collectors;

public class RoundPlayerService {

    private Deque<RoundPlayer> roundPlayers;

    RoundPlayerService(final Deque<Player> roundPlayers) {
        this.roundPlayers = convertToRoundPlayers(roundPlayers);
    }

    Deque<RoundPlayer> getRoundPlayers() {
        return (Deque<RoundPlayer>) Collections.unmodifiableCollection(roundPlayers);
    }

    private Deque<RoundPlayer> convertToRoundPlayers(final Deque<Player> players) {
        return players.stream()
                .map(player -> new RoundPlayer(player.getId(), player.getBalance()))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    void chargeBlinds(final Blinds blinds) {
        //dealer gives 0 blind
        roundPlayers.addLast(roundPlayers.pollFirst());
        //small gives small blind
        bid(blinds.getSmall());
        //big gives big blind
        bid(blinds.getBig());
    }

    void pickWinner(int playerId) {
        roundPlayers.stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .ifPresent(player -> player.winMoney(calculateTotalPot()));
    }



    ///TODO!!! wszystkie metody za !


    void bid(int amount) {
        RoundPlayer player = roundPlayers.pollFirst();
        player.bid(amount);
        roundPlayers.addLast(player);
        // trigger websocket to see that next player has changed
        // trigger websocket to see that totalPot has changed
    }

    void fold() {
        RoundPlayer player = roundPlayers.pollFirst();
        player.fold();
        roundPlayers.addLast(player);
    }

    int calculateTotalPot() {
        return roundPlayers.stream()
                .mapToInt(RoundPlayer::getRoundBid)
                .sum();
    }

    boolean playersBidsAreEqual() {
        return roundPlayers.stream()
                .filter(RoundPlayer::isInGame)
                .map(RoundPlayer::getTurnBid)
                .distinct()
                .count() == 1;
    }
}
