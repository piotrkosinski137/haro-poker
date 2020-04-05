package app.domain.round;

import static app.domain.round.RoundStage.INIT;

import app.domain.card.CardDeckService;
import app.domain.game.Blinds;
import app.domain.player.Player;
import java.util.Deque;
import org.springframework.stereotype.Service;

@Service
public class RoundService {

    private final CardDeckService cardDeckService;
    private RoundPlayerService roundPlayerService;
    private Round round;

    public RoundService(final CardDeckService cardDeckService) {
        this.cardDeckService = cardDeckService;
    }

    /**
     *  Entrypoint for each new round:
     *
     *  - reset round
     *  - set roundPlayers
     *  - give cards to players
     *  - charge blinds
     * */
    public void startRound(final Deque<Player> players, final Blinds blinds) {
        round = new Round();
        roundPlayerService = new RoundPlayerService(players);
        cardDeckService.shuffleNewDeck();
        roundPlayerService.chargeBlinds(blinds);
        giveCardsToPlayers();
    }

    public Deque<RoundPlayer> finishRound(final int winnerPlayerId) {
        //TODO sprawdzenie czy runda jest rozpoczęta
        roundPlayerService.pickWinner(winnerPlayerId);
        return roundPlayerService.getRoundPlayers();
    }

    /*
     * Eg. We finished first betting and now:
     * - three cards will appear on table
     * - roundStage will change to from Init to Flop
     * - roundPlayers tourBet will be zero (it contains only money for current stage)
     * */
    public void nextStage() {
        //TODO sprawdzenie czy runda jest rozpoczęta
        putCardsOnTable();
        round.changeRoundStage();
        roundPlayerService.getRoundPlayers().forEach(RoundPlayer::prepareForNextRound);
    }

    private void giveCardsToPlayers() {
        roundPlayerService.getRoundPlayers().forEach(player -> player.putCardsInHand(cardDeckService.getCards(2)));
    }

    private void putCardsOnTable() {
        if (round.getRoundStage() == INIT) {
            round.putCardsOnTable(cardDeckService.getCards(3));
        } else {
            round.putCardsOnTable(cardDeckService.getCards(1));
        }
    }
}


   /* *//*
     * We will take precaution that only current player can bet. Then there is no need to track id
     * Current roundPlayer is always at the beginning of deque
     * *//*
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

    public int getTotalPot() {
        return round.calculateTotalPot();
    }*/