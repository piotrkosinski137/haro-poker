package app.domain.round;

import app.domain.player.Player;
import java.util.Collection;
import java.util.Deque;
import java.util.Stack;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

  private Round round;

  public RoundService() {
    this.round = new Round();
  }

  public void startRound(Deque<Player> players) {
    round.addRoundPlayers(convertPlayersToRoundPlayers(players));
  }

  private Collection<RoundPlayer> convertPlayersToRoundPlayers(Deque<Player> players) {
    return players.stream()
        .map(player -> new RoundPlayer(player.getId(), player.getBalance()))
        .collect(Collectors.toList());
  }

  public Stack<RoundPlayer> addPlayersToRound(Stack<Player> players) {
    return null;
  }

  public void takeBlinds() {
  }

  public void giveCardsToPlayers() {

  }

  public void putCardsOnTable() {

  }

  public void changeRoundStage() {
  }

  public void resetRound() {
    round = new Round();
  }
}
