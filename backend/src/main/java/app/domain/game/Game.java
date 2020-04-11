package app.domain.game;

import app.domain.game.exceptions.GameIsFull;
import app.domain.game.exceptions.GamePlayerNotFound;
import app.domain.player.GamePlayer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

class Game {

    private final Deque<GamePlayer> gamePlayers;
    private final Blinds blinds;
    private long gameTimeStamp;
    private int entryFee;

    Game() {
        gamePlayers = new ArrayDeque<>();
        blinds = new Blinds();
    }

    UUID addPlayer(GamePlayer gamePlayer) {
        gamePlayers.addLast(gamePlayer);
        return gamePlayer.getId();

    }

    Blinds getBlinds() {
        return blinds;
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

    boolean isFull() {
        return gamePlayers.size() == 7;
    }

    void changeActiveStatus(UUID id, boolean isActive) { //???? nie powinno tak byc
        GamePlayer gamePlayer = gamePlayers.stream().filter(player -> player.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new GamePlayerNotFound(id));
        gamePlayer.setActive(isActive);
    }

    void updateBlinds(int small) {
        blinds.setBlinds(small);
    }

    int findEmptyTableNumber() {
        List<Integer> tableNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        gamePlayers.forEach(player -> tableNumbers.remove(player.getTableNumber()));

        if (tableNumbers.isEmpty()) {
            throw new GameIsFull();
        }
        return tableNumbers.get(0);
    }

    long getGameTimeStamp() {
        return gameTimeStamp;
    }

    void setGameTimeStamp(long gameTimeStamp) {
        this.gameTimeStamp = gameTimeStamp;
    }

    int getEntryFee() {
        return entryFee;
    }

    void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }
}
