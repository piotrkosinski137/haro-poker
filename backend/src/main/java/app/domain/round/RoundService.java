package app.domain.round;

import app.domain.card.Card;
import app.domain.card.CardDeckService;
import app.domain.event.RoundChanged;
import app.domain.event.RoundPlayersChanged;
import app.domain.game.Blinds;
import app.domain.player.GamePlayer;
import app.domain.round.exception.PlayerNotFound;
import app.domain.round.exception.RoundNotStarted;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoundService {

    private final CardDeckService cardDeckService;
    private RoundPlayerService roundPlayerService;
    private final ApplicationEventPublisher publisher;
    private final Round round;

    public RoundService(final CardDeckService cardDeckService, ApplicationEventPublisher publisher) {
        this.cardDeckService = cardDeckService;
        this.publisher = publisher;
        round = new Round();
    }

    public void startRound(final Deque<GamePlayer> gamePlayers, final Blinds blinds) {
        round.reset();
        roundPlayerService = new RoundPlayerService(gamePlayers);
        cardDeckService.shuffleNewDeck();
        roundPlayerService.chargeBlinds(blinds);
        roundPlayerService.setCurrentPlayer();
        giveCardsToPlayers();
        publisher.publishEvent(new RoundChanged(this, round));
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
    }

    public Deque<RoundPlayer> finishRound(final UUID winnerPlayerId) {
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        roundPlayerService.pickWinner(winnerPlayerId);
        publisher.publishEvent(new RoundChanged(this, round));
        return roundPlayerService.getRoundPlayers();
    }

    public void startNextStage() {  //TODO think about this to automatically finish stage
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        round.changeRoundStage();
        putCardsOnTable();
        roundPlayerService.getRoundPlayers().forEach(RoundPlayer::prepareForNextStage);
        publisher.publishEvent(new RoundChanged(this, round));
    }

    public synchronized Set<Card> getPlayerCards(final String id) {
        return roundPlayerService.getRoundPlayers().stream()
                .filter(roundPlayer -> roundPlayer.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(PlayerNotFound::new)
                .getCardsInHand();
    }

    public Round getRound() {
        return round;
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
        checkGameConditions();
    }

    public void allIn() {
        roundPlayerService.allIn();
        checkGameConditions();
    }

    public void fold() {
        roundPlayerService.fold();
        checkGameConditions();
        // Change in round triggers fetching cards from player - need to consider different approach to fetch player cards
        publisher.publishEvent(new RoundChanged(this, round));
    }

    private void checkGameConditions() {
        checkNumberOfPlayers();
  //      if (!roundPlayerService.playersBidsAreEqual()) {
            roundPlayerService.setNextPlayer();
            publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
//        } //else if (roundPlayerService.isBigBlindPlayer() && round.getRoundStage() == RoundStage.INIT) {
//        else {
//            startNextStage();
//        }
    }

    private void checkNumberOfPlayers() {
        if (roundPlayerService.getRoundPlayers().size() == 1) {
            final UUID id = roundPlayerService.getRoundPlayers().getFirst().getId();
            finishRound(id);
        }
    }

    private void giveCardsToPlayers() {
        roundPlayerService.getRoundPlayers().forEach(player -> player.putCardsInHand(cardDeckService.getCards(2)));
    }

    private void putCardsOnTable() {
        round.putCardsOnTable(cardDeckService.getCards(round.getRoundStage().getCardAmount()));
    }

    public void removeRoundPlayer(UUID id) {
        roundPlayerService.removeRoundPlayer(id);
    }
}
