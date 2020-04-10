package app.domain.round;

import app.domain.card.Card;
import app.domain.card.CardDeckService;
import app.domain.game.Blinds;
import app.domain.player.GamePlayer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoundService {

    private final CardDeckService cardDeckService;
    private RoundPlayerService roundPlayerService;
    private Round round;

    public RoundService(final CardDeckService cardDeckService) {
        this.cardDeckService = cardDeckService;
        round = new Round();
    }

    /**
     * Entrypoint for each new round:
     * <p>
     * - reset round
     * - set roundPlayers
     * - give cards to players
     * - charge blinds
     */
    public void startRound(final Deque<GamePlayer> gamePlayers, final Blinds blinds) {
        roundPlayerService = new RoundPlayerService(gamePlayers);
        cardDeckService.shuffleNewDeck();
        roundPlayerService.chargeBlinds(blinds);
        roundPlayerService.setCurrentPlayer();
        giveCardsToPlayers();
    }

    public Deque<RoundPlayer> finishRound(final UUID winnerPlayerId) {
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

    public Set<Card> getTableCards() {
        return round.getTableCards();
    }

    private void giveCardsToPlayers() {
        roundPlayerService.getRoundPlayers().forEach(player -> player.putCardsInHand(cardDeckService.getCards(2))); //dont like this magic number
    }

    private void putCardsOnTable() {
        round.putCardsOnTable(cardDeckService.getCards(round.getRoundStage().getCardAmount()));
    }

    public Collection<RoundPlayer> getPlayers() {
        //TODO either RoundPlayerService will be initialized during application startup or I need a flag to check if game has started or not
        if (roundPlayerService == null) {
            return new HashSet<>();
        } else {
            return roundPlayerService.getRoundPlayers();
        }
    }

    public void bid(int amount) {
        roundPlayerService.bid(amount);
        roundPlayerService.setPlayerWithTurn();
    }
}
