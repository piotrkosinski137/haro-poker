package app.domain.game;

import app.domain.player.Player;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

class Game {

  private final Deque<Player> players;
  private int entryFee;

  Game() {
    players = new ArrayDeque<>();
  }

  public int getEntryFee() {
    return entryFee;
  }

  public Deque<Player> getActivePlayers() {
    return players.stream()
        .filter(Player::isActive)
        .collect(Collectors.toCollection(ArrayDeque::new));
  }

  public void movePlayers() {
    players.addLast(players.pollFirst());
  }
}
