package app.domain.round;

import app.domain.player.Player;
import app.domain.roundPlayer.RoundPlayer;
import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

    private Logger logger = LoggerFactory.getLogger(RoundService.class);
    private Round round;

    //service methods

    public RoundService() {
        this.round = new Round();
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

    public void nexturn() {
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

}
