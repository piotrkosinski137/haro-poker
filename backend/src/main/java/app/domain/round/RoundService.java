package app.domain.round;

import app.domain.card.CardDeckService;
import app.domain.game.Blinds;
import org.springframework.stereotype.Service;

import java.util.Deque;

import static app.domain.round.RoundStage.INIT;
import static app.domain.round.RoundStage.RIVER;

@Service
public class RoundService {

    private Round round;
    private final CardDeckService cardDeckService;

    public RoundService(CardDeckService cardDeckService) {
        this.cardDeckService = cardDeckService;
        round = new Round();
    }

    /*
     *  Entrypoint for each new round:
     *
     *  - reset round
     *  - set roundPlayers
     *  - charge blinds
     *  - give cards to players
     * */
    public void startRound(Deque<RoundPlayer> players, Blinds blinds) {
        resetRound();
        round.addRoundPlayers(players);
        round.chargeBlinds(blinds);
        cardDeckService.shuffleNewDeck();
        giveCardsToPlayers();
    }

    /*
     * We will take precaution that only current player can bet. Then there is no need to track id
     * Current roundPlayer is always at the beginning of deque
     * */
    public void bid(int amount) {
        round.bid(amount);
        if (round.playersBidsAreEqual()) {
            if (round.getRoundStage() != RIVER) {
                nextStage();
            } else {
                // the end of round - now admin should pick winner
            }
        }
    }

    public void fold() {
        round.fold();
    }

    /*
     * Eg. We finished first betting and now:
     * - three cards will appear on table
     * - roundStage will change to from Init to Flop
     * - roundPlayers tourBet will be zero (it contains only money for current stage)
     * */
    private void nextStage() {
        putCardsOnTable();
        round.nextStage();
    }

    private void giveCardsToPlayers() {
        for (RoundPlayer player : round.getRoundPlayers()) {
            player.putCardsInHand(cardDeckService.getCards(2));
        }
    }

    private void putCardsOnTable() {
        if (round.getRoundStage() == INIT) {
            round.putCardsOnTable(cardDeckService.getCards(3));
        } else {
            round.putCardsOnTable(cardDeckService.getCards(1));
        }
    }

    public int getTotalPot() {
        return round.calculateTotalPot();
    }

    public void resetRound() {
        round = new Round();
    }

    public Deque<RoundPlayer> finishRound(int playerId) {
        round.pickWinner(playerId);
        return round.getRoundPlayers();
    }
}
