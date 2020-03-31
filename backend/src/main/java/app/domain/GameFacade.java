package app.domain;

import app.domain.player.PlayerCommandService;
import app.domain.round.RoundService;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;

/*
 * Will have trend to grow, need to split it
 * */
@Service
public class GameFacade {

  private final PlayerCommandService playerCommandService;
  private final RoundService roundService;

  public GameFacade(PlayerCommandService playerCommandService, RoundService roundService) {
    this.playerCommandService = playerCommandService;
    this.roundService = roundService;
  }

  public void payToWinner(UUID playerId) {
    playerCommandService.earnMoney(playerId, roundService.getPot());
  }

  public void bet(UUID playerId, BigDecimal amount) {
    playerCommandService.bet(playerId, amount);
  }

  public void check(UUID playerId) {
  }

  public void fold(UUID playerId) {
  }

  /*
   * Adds player to playerRepo
   * Adds player to round. Round holds info, which player has folded and which is still playing
   * */
  public void addPlayer(String name) {
    roundService.addPlayer(playerCommandService.addPlayer(name));
  }

  /*
   * When player doesn't have money
   * */
  public void removePlayer(UUID playerId) {
    playerCommandService.remove(playerId);
    roundService.removePlayer(playerId);
  }
}
