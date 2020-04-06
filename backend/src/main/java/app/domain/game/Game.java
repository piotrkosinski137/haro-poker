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

    int getEntryFee() {
        return entryFee;
    }

    void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }

    Deque<Player> getActivePlayers() {
        return players.stream()
                .filter(Player::isActive)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    void rotatePlayers() {
        players.addLast(players.pollFirst());
    }
}
