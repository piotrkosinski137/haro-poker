package app.domain.round;

import app.domain.card.Card;
import app.domain.card.CardDeckService;
import app.domain.event.RoundPlayersChanged;
import app.domain.event.TableCardsChanged;
import app.domain.game.Blinds;
import app.domain.player.GamePlayer;
import app.domain.round.exception.RoundNotStarted;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoundService {

    private final CardDeckService cardDeckService;
    private RoundPlayerService roundPlayerService;
    private final ApplicationEventPublisher publisher;
    private Round round;

    public RoundService(final CardDeckService cardDeckService, ApplicationEventPublisher publisher) {
        this.cardDeckService = cardDeckService;
        this.publisher = publisher;
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
        round = new Round();
        roundPlayerService = new RoundPlayerService(gamePlayers);
        cardDeckService.shuffleNewDeck();
        roundPlayerService.chargeBlinds(blinds);
        roundPlayerService.setCurrentPlayer();
        giveCardsToPlayers();
    }

    public Deque<RoundPlayer> finishRound(final UUID winnerPlayerId) {
        if (Objects.isNull(round)) {
            throw new RoundNotStarted();
        }
        roundPlayerService.pickWinner(winnerPlayerId);
        round = null;
        return roundPlayerService.getRoundPlayers();
    }

    /**
     * Eg. We finished first betting and now:
     * - three cards will appear on table
     * - roundStage will change to from Init to Flop
     * - roundPlayers tourBet will be zero (it contains only money for current stage)
     * */
    public void startNextStage() {
        if (Objects.isNull(round)) {
            throw new RoundNotStarted();
        }
        round.changeRoundStage();
        putCardsOnTable();
        roundPlayerService.getRoundPlayers().forEach(RoundPlayer::prepareForNextStage);
        publisher.publishEvent(new TableCardsChanged(this, round.getTableCards()));
    }


    // TODO Try to subscribe to cards stream as well as roudplayers when game will start
    public Set<Card> getTableCards() {
        if (Objects.isNull(round)) {
            return new HashSet<>();
        } else {
            return round.getTableCards();
        }
    }

    private void giveCardsToPlayers() {
        roundPlayerService.getRoundPlayers().forEach(player -> player.putCardsInHand(cardDeckService.getCards(2)));
    }

    private void putCardsOnTable() {
        round.putCardsOnTable(cardDeckService.getCards(round.getRoundStage().getCardAmount()));
    }

    public Collection<RoundPlayer> getPlayers() {
        //TODO either RoundPlayerService will be initialized during application startup or I need a flag to check if game has started or not
        if (Objects.isNull(roundPlayerService)) {
            return new HashSet<>();
        } else {
            return roundPlayerService.getRoundPlayers();
        }
    }

    public void bid(int amount) {
        roundPlayerService.bid(amount);
        roundPlayerService.setNextPlayer();
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
    }

    public void fold() {
        roundPlayerService.fold();
        roundPlayerService.setNextPlayer();
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
    }
}
