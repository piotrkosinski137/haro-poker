package app.domain.round;

import static app.domain.round.PlayerStatus.FOLDED;
import static app.domain.round.PlayerStatus.IN_GAME;
import static app.domain.round.RoundStage.FLOP;
import static app.domain.round.RoundStage.INIT;
import static app.domain.round.RoundStage.RIVER;
import static app.domain.round.RoundStage.TURN;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import app.domain.card.Card;

class Round {

  private Set<Card> tableCards;
  private RoundStage stage;
  private Map<UUID, PlayerStatus> players;
  private BigDecimal pot;

  public Round() {
    tableCards = new HashSet<>();
    stage = RoundStage.INIT;
    players = new HashMap<>();
    pot = BigDecimal.ZERO;
  }

  public void restartRound() {
    tableCards = new HashSet<>();
    stage = RoundStage.INIT;
    players = resetPlayersStatus();
    pot = BigDecimal.ZERO;
  }

  public void addPlayer(UUID playerId) {
    players.put(playerId, IN_GAME);
  }

  public void removePlayer(UUID playerId) {
    players.remove(playerId);
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

  public RoundStage getRoundStage() {
    return stage;
  }

  public void changeRoundStage() {
    switch (stage) {
      case INIT:
        stage = FLOP;
        break;
      case FLOP:
        stage = TURN;
        break;
      case TURN:
        stage = RIVER;
        break;
      case RIVER:
        stage = INIT; // to consider
        break;
      default:
        break;
    }
  }

  private Map<UUID, PlayerStatus> resetPlayersStatus() {
    return players.entrySet().stream()
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            entrySet -> entrySet.setValue(IN_GAME)
        ));
  }
}
