package app.domain.game;

import app.domain.player.GamePlayer;

import java.util.*;
import java.util.stream.Collectors;

class Game {

    private final Deque<GamePlayer> gamePlayers;
    private Blinds blinds;
    private int entryFee;

    Game() {
        gamePlayers = new ArrayDeque<>();
        blinds = new Blinds();
    }

    UUID addPlayer(String playerName) {
        GamePlayer gamePlayer = new GamePlayer(playerName, findEmptyTableNumber());
        gamePlayers.addLast(gamePlayer);
        return gamePlayer.getId();
    }

    private int findEmptyTableNumber() {
        List<Integer> tableNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        gamePlayers.forEach(player -> tableNumbers.remove(player.getTableNumber()));

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

    Deque<GamePlayer> getActivePlayers() {
        return gamePlayers.stream()
                .filter(GamePlayer::isActive)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    Collection<GamePlayer> getGamePlayers() {
        return Collections.unmodifiableCollection(gamePlayers);
    }

    void rotatePlayers() {
        gamePlayers.addLast(gamePlayers.pollFirst());
    }

    public boolean isFull() {
        return gamePlayers.size() == 7;
    }
}
