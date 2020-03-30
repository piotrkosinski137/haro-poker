package app.domain;

import static app.domain.PlayerStatus.FOLDED;
import static app.domain.PlayerStatus.IN_GAME;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Round {

  private Set<Card> tableCards = new HashSet<>();
  private RoundStage stage;
  private Map<UUID, PlayerStatus> players;
  private BigDecimal pot;

  public Round() {
    stage = RoundStage.INIT;
    pot = BigDecimal.ZERO;
    players = new HashMap<>();
  }

  public Round(Set<UUID> playerIds) {
    this();
    playerIds.forEach(this::addPlayer);
  }

  public void addPlayer(UUID playerId) {
    players.put(playerId, IN_GAME);
  }

  public void fold(UUID playerId) {
    players.replace(playerId, FOLDED);
  }

  public void addTableCards(Set<Card> cards) {
    tableCards.addAll(cards);
  }

  public void clearTableCards() {
    tableCards.clear();
  }

  public Set<Card> getTableCards() {
    return tableCards;
  }

  public boolean hasPlayerFolded(UUID playerId) {
    return players.get(playerId) == FOLDED;
  }

  public BigDecimal getPot() {
    return pot;
  }
}
