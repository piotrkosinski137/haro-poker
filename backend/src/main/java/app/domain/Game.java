package app.domain;

import app.domain.exception.PlayerNotFound;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Game {

  private Deque<Player> players = new ArrayDeque<>();
  private Round round = new Round();

  public Game() {
  }

  public void bet(UUID playerId, BigDecimal amount) {
    Player player = findPlayerById(playerId);
    player.bet(amount);
    nextPlayer();
  }

  public void fold(UUID playerId) {
    round.fold(playerId);
    nextPlayer();
  }

  public void check(UUID playerId) {
    nextPlayer();
  }

  private void nextPlayer() {
    // if last player left then skip logic
    Player currentPlayer = players.removeFirst();
    players.addLast(currentPlayer);
    if (round.hasPlayerFolded(players.getFirst().getId())) {
      nextPlayer();
    }
  }

  public void pickWinner(UUID playerId) {
    Player player = findPlayerById(playerId);
    player.earnMoney(round.getPot());
  }

  public void restartRound() {
    round = new Round(getPlayerIds());
  }

  public UUID getCurrentPlayer() {
    return players.getFirst().getId();
  }

  public void addPlayer(Player player) {
    if (nameAlreadyExist(player.getName())) {
      player.addNumberToName();
    }
    players.addFirst(player);
    round.addPlayer(player.getId());
  }

  private Set<UUID> getPlayerIds() {
    return players.stream().map(Player::getId).collect(Collectors.toSet());
  }

  private boolean nameAlreadyExist(String name) {
    return players.stream().anyMatch(player -> player.getName().equals(name));
  }

  private Player findPlayerById(UUID playerId) {
    return players.stream()
        .filter(p -> p.getId().equals(playerId))
        .findAny()
        .orElseThrow(() -> new PlayerNotFound(playerId));
  }
}
