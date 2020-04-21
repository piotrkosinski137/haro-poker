package app.domain.round;

import app.domain.card.CardDeckService;
import app.domain.card.CardDto;
import app.domain.game.Blinds;
import app.domain.game.GamePlayerDto;
import app.domain.round.exception.PlayerNotFound;
import app.domain.round.exception.RoundNotStarted;
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
public class RoundServiceImpl {

    private final CardDeckService cardDeckService;
    private final RoundPlayerServiceImpl roundPlayerService;
    private final ApplicationEventPublisher publisherGlobal;
    private final Round round;

    public RoundServiceImpl(final CardDeckService cardDeckService, RoundPlayerServiceImpl roundPlayerService,
            ApplicationEventPublisher publisherGlobal, Round round) {
        this.cardDeckService = cardDeckService;
        this.roundPlayerService = roundPlayerService;
        this.publisherGlobal = publisherGlobal;
        this.round = round;
    }

    public void startRound(final Deque<GamePlayerDto> gamePlayers, final Blinds blinds) {
        cardDeckService.shuffleNewDeck();
        round.start();
        roundPlayerService.startRound(gamePlayers, blinds);
        giveCardsToPlayers();
        publisherGlobal.publishEvent(new RoundChanged(this, getRound()));
    }

    private void startNextStage() {
        changeRoundStage();
        if (round.getRoundStage() == RoundStage.FINISHED) {
            roundPlayerService.showRoundPlayersSummary();
        } else {
            proceedNewStage();
        }
    }

    private void changeRoundStage() {
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        round.changeRoundStage();
    }

    private void proceedNewStage() {
        putCardsOnTable();
        roundPlayerService.preparePlayersForNextStage();
        publisherGlobal.publishEvent(new RoundChanged(this, getRound()));
    }

    private void putCardsOnTable() {
        round.putCardsOnTable(cardDeckService.getCards(round.getRoundStage().getCardAmount()));
    }

    public void manualNextRound() {
        changeRoundStage();
        putCardsOnTable();
        publisherGlobal.publishEvent(new RoundChanged(this, getRound()));
    }

    public void finishRoundWithWinner(final UUID winnerPlayerId) { //todo move
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        roundPlayerService.pickWinner(winnerPlayerId);
    }

    public void manualFinishRound(UpdatePlayerBalanceRequest updateBalances) { //todo move
        roundPlayerService.updatePlayersBalance(updateBalances);
    }

    public synchronized Set<CardDto> getPlayerCards(final String id) {  //todo move
        return roundPlayerService.getRoundPlayers().stream()
                .filter(roundPlayer -> roundPlayer.getId().toString().equals(id))
                .findFirst()
                .orElseThrow(PlayerNotFound::new)
                .getCardsInHand();
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
            roundPlayerService.showRoundPlayersSummary();
        } else {
            roundPlayerService.giveTurnToCurrentPlayer();
        }
    }

    private boolean playerCalledAllIn() {
        RoundPlayer currentPlayer = roundPlayerService.getFirstPlayerInGame();
        return currentPlayer.getBalance() == 0 || playerBidHighestAllIn(currentPlayer);
    }

    private void handleMultiplePlayersAction() {
        if (isStageOver()) {
            if (round.getRoundStage() == RoundStage.RIVER) {
                roundPlayerService.showRoundPlayersSummary();
            } else {
                startNextStage();
            }
        } else {
            if (!roundPlayerService.giveTurnToCurrentPlayer()) {
                roundPlayerService.showRoundPlayersSummary();
            } else {
                publisherGlobal.publishEvent(new RoundPlayersChanged(this, getPlayers()));
            }
        }
    }

    private boolean isStageOver() {
        return roundPlayerService.playersBidsAreEqual() && allHadTurn();
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

    public RoundDto getRound() {
        return new RoundDto(round.getRoundStage().name(), round.getTableCards());
    }

    public Collection<RoundPlayer> getPlayers() {
        if (Objects.isNull(roundPlayerService)) {
            return new HashSet<>();
        } else {
            return roundPlayerService.getRoundPlayers();
        }
    }
}
