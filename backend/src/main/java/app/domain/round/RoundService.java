package app.domain.round;

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
    public void startNextStage() {
        //TODO sprawdzenie czy runda jest rozpoczęta
        round.changeRoundStage();
        putCardsOnTable();
        roundPlayerService.getRoundPlayers().forEach(RoundPlayer::prepareForNextStage);
    }

    private void giveCardsToPlayers() {
        roundPlayerService.getRoundPlayers().forEach(player -> player.putCardsInHand(cardDeckService.getCards(2))); //dont like this magic number
    }

    private void putCardsOnTable() {
        round.putCardsOnTable(cardDeckService.getCards(round.getRoundStage().getCardAmount()));
    }
}