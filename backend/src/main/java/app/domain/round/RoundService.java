package app.domain.round;

import java.math.BigDecimal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

  private Logger logger = LoggerFactory.getLogger(RoundService.class);
  private Round round;

  public RoundService() {
    this.round = new Round();
  }

  public BigDecimal getPot() {
    return round.getPot();
  }

  public void nextRound() {
    switch (round.getRoundStage()) {
      case INIT:
        break;
      case FLOP:
        //round.addTableCards(cardService.getCards(FLOP.getCardAmount()));
        break;
      case TURN:
        //round.addTableCards(cardService.getCards(TURN.getCardAmount()));
        break;
      case RIVER:
        //round.addTableCards(cardService.getCards(RIVER.getCardAmount()));
        break;
      default:
        logger.info("It was last round");
        break;
    }
    round.changeRoundStage();
  }

  public void addPlayer(UUID playerId) {
    round.addPlayer(playerId);
  }

  public void removePlayer(UUID playerId) {
    round.addPlayer(playerId);
  }
}
