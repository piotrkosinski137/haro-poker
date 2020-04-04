package app.domain.game;

import app.domain.player.Player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

class Game {

    private final Deque<Player> players;
    private Blinds blinds;
    private int entryFee;

    Game() {
        players = new ArrayDeque<>();
        blinds = new Blinds();
    }

    void addPlayer(Player player) {
        players.addLast(player);
    }

    Blinds getBlinds() {
        return blinds;
    }

    Deque<Player> getActivePlayers() {
        return players.stream()
                .filter(Player::isActive)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    void movePlayers() {
        players.addLast(players.pollFirst());
    }

    void updateBalance(int playerId, int amount) {
        players.stream()
                .filter(player -> player.getId() == playerId)
                .forEach(player -> player.updateBalance(amount));
    }
}
