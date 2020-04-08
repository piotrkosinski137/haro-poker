package app.domain.game;

import app.domain.player.Player;

import java.util.*;
import java.util.stream.Collectors;

class Game {

    private final Deque<Player> players;
    private Blinds blinds;
    private int entryFee;

    Game() {
        players = new ArrayDeque<>();
        blinds = new Blinds();
    }

    UUID addPlayer(String playerName) {
        Player player = new Player(playerName, findEmptyTableNumber());
        players.addLast(player);
        return player.getId();
    }

    private int findEmptyTableNumber() {
        List<Integer> tableNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        players.forEach(player -> tableNumbers.remove(Integer.valueOf(player.getTableNumber())));

        if (tableNumbers.isEmpty()) {
            throw new GameIsFull();
        }
        return tableNumbers.get(0);
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

    Collection<Player> getPlayers() {
        return Collections.unmodifiableCollection(players);
    }

    void rotatePlayers() {
        players.addLast(players.pollFirst());
    }

    public boolean isFull() {
        return players.size() == 7;
    }
}
