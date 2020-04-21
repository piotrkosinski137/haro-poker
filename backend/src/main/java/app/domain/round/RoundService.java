package app.domain.round;

import app.domain.card.CardDeckService;
import app.domain.card.CardDto;
import app.domain.event.RoundChanged;
import app.domain.event.RoundPlayersChanged;
import app.domain.game.Blinds;
import app.domain.game.GamePlayer;
import app.domain.round.exception.PlayerNotFound;
import app.domain.round.exception.RoundNotStarted;
import app.web.rest.dto.PlayerMoney;
import app.web.rest.dto.UpdatePlayerBalanceRequest;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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

    public void startRound(final Deque<GamePlayer> gamePlayers, final Blinds blinds) { //todo make gamePlayers package
        round.reset();
        roundPlayerService = new RoundPlayerService(gamePlayers);
        cardDeckService.shuffleNewDeck();
        roundPlayerService.chargeBlinds(blinds);
        roundPlayerService.giveTurnToCurrentPlayer();
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
        changeRoundStage();
        if (round.getRoundStage() == RoundStage.FINISHED) {
            showRoundSummary();
        } else {
            proceedNewStage();
        }
    }

    public void changeRoundStage() {
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        round.changeRoundStage();
    }

    private void proceedNewStage() {
        putCardsOnTable();
        roundPlayerService.preparePlayersForNextStage();
        roundPlayerService.putProperPlayerOnTop();
        publisher.publishEvent(new RoundChanged(this, round));
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
    }

    private void showRoundSummary() {
        publisher.publishEvent(new RoundPlayersChanged(this, getPlayers(), true));
    }

    public synchronized Set<CardDto> getPlayerCards(final String id) {
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

    public void allIn() {
        roundPlayerService.allIn();
        checkGameConditions();
    }

    public void bid(int amount) {
        roundPlayerService.bid(amount);
        checkGameConditions();
    }

    public void fold() {
        roundPlayerService.fold();
        checkGameConditions();
    }

    private void checkGameConditions() {
        if (isOnePlayerInGame()) {
            handleLastPlayerAction();
        } else {
            handleMultiplePlayersAction();
        }
    }

    private boolean isOnePlayerInGame() {
        return roundPlayerService.getRoundPlayers().stream()
                .filter(RoundPlayer::isInGame)
                .collect(Collectors.toSet()).size() == 1;
    }

    private void handleLastPlayerAction() {
        if (playerCalledAllIn()) {
            showRoundSummary();
        } else {
            roundPlayerService.giveTurnToCurrentPlayer();
            publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
        }
    }

    private void handleMultiplePlayersAction() {
        if (isStageOver()) {
            if (round.getRoundStage() == RoundStage.RIVER) {
                showRoundSummary();
            } else {
                startNextStage();
            }
        } else {
            if (!roundPlayerService.giveTurnToCurrentPlayer()) {
                showRoundSummary();
            } else {
                publisher.publishEvent(new RoundPlayersChanged(this, getPlayers()));
            }
        }
    }

    private boolean isStageOver() {
        return roundPlayerService.playersBidsAreEqual() && allHadTurn();
    }

    private boolean playerCalledAllIn() {
        RoundPlayer currentPlayer = roundPlayerService.getFirstPlayerInGame();
        return currentPlayer.getBalance() == 0 || playerBidHighestAllIn(currentPlayer);
    }

    private boolean playerBidHighestAllIn(RoundPlayer currentPlayer) {
        return getPlayersWithAllIn().stream()
                .allMatch(player -> currentPlayer.getRoundBid() >= player.getRoundBid());
    }

    public List<RoundPlayer> getPlayersWithAllIn() {
        return roundPlayerService.getRoundPlayers().stream()
                .filter(RoundPlayer::hasAllIn)
                .collect(Collectors.toList());
    }

    private boolean allHadTurn() {
        return roundPlayerService.allHadTurnInStage();
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

    public void manualNextRound() {
        changeRoundStage();
        putCardsOnTable();
        publisher.publishEvent(new RoundChanged(this, round));
    }
}
