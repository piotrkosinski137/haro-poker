package app.domain.round;

import app.domain.card.Card;
import app.domain.card.CardDeckService;
import app.domain.event.RoundChanged;
import app.domain.event.RoundPlayersChanged;
import app.domain.game.Blinds;
import app.domain.player.GamePlayer;
import app.domain.round.exception.PlayerNotFound;
import app.domain.round.exception.RoundNotStarted;
import app.web.rest.dto.PlayerMoney;
import app.web.rest.dto.UpdatePlayerBalanceRequest;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
        roundPlayerService.setNextPlayer();
        giveCardsToPlayers();
        publisher.publishEvent(new RoundChanged(this, round));
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
    }

    public Deque<RoundPlayer> finishRound(final UUID winnerPlayerId) {
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        roundPlayerService.pickWinner(winnerPlayerId);
        return roundPlayerService.getRoundPlayers();
    }

    public void startNextStage() {
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        round.changeRoundStage();
        if (round.getRoundStage() == RoundStage.FINISHED) {
            showRoundSummary();
        } else {
            proceedNewStage();
        }
    }

    private void proceedNewStage() {
        putCardsOnTable();
        roundPlayerService.getRoundPlayers().forEach(RoundPlayer::prepareForNextStage);
        roundPlayerService.putProperPlayerOnTop();
        publisher.publishEvent(new RoundChanged(this, round));
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
    }

    private void showRoundSummary() {
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers(), true));
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
    }

    private void checkGameConditions() {
        checkNumberOfPlayers();
        if (!roundPlayerService.playersBidsAreEqual() || roundPlayerService.calculateTurnPot() == 0) {
            roundPlayerService.setNextPlayer();
            publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
        } //else if (roundPlayerService.isCurrentPlayerOnSmallBlind() && round.getRoundStage() == RoundStage.INIT) {
        //roundPlayerService.setNextPlayer();
        // publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
        //}
        else {
            startNextStage();
        }
    }

    private void checkNumberOfPlayers() {
        final int playersInGame = roundPlayerService.getRoundPlayers().stream().filter(RoundPlayer::isInGame).collect(Collectors.toSet()).size();
        if (playersInGame ==1) {
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

    public void updatePlayersBalance(UpdatePlayerBalanceRequest updateBalances) {
        roundPlayerService.getRoundPlayers().forEach(player -> player.winMoney(
                updateBalances.getPlayerMoney().stream()
                        .filter(updateBalance -> updateBalance.getPlayerId().equals(player.getId().toString()))
                        .findFirst()
                        .orElse(new PlayerMoney())
                        .getMoney()
        ));
    }
}
