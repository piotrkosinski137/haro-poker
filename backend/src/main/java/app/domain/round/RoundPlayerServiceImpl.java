package app.domain.round;

import static app.domain.round.Position.BIG_BLIND;
import static app.domain.round.Position.DEALER;
import static app.domain.round.Position.SMALL_BLIND;

import app.domain.game.Blinds;
import app.domain.game.GamePlayerDto;
import app.domain.game.GamePlayerService;
import app.web.rest.dto.PlayerMoney;
import app.web.rest.dto.UpdatePlayerBalanceRequest;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RoundPlayerServiceImpl {

    private final Round round;
    private final ApplicationEventPublisher publisherGlobal;
    private final GamePlayerService gamePlayerService;

    public RoundPlayerServiceImpl(Round round, ApplicationEventPublisher publisherGlobal, GamePlayerService gamePlayerService) {
        this.round = round;
        this.publisherGlobal = publisherGlobal;
        this.gamePlayerService = gamePlayerService;
    }

    void startRound(Deque<GamePlayerDto> gamePlayers, Blinds blinds) {
        if (gamePlayers.size() < 2) {
            //exception
        }
        round.addPlayers(convertToRoundPlayers(gamePlayers));
        chargeBlinds(blinds);
        giveTurnToCurrentPlayer();
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, getRoundPlayers()));
    }

    private Deque<RoundPlayer> convertToRoundPlayers(final Deque<GamePlayerDto> gamePlayers) {
        return gamePlayers.stream()
                .map(player -> new RoundPlayer(UUID.fromString(player.getId()), player.getBalance(), player.getTableNumber()))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private void chargeBlinds(final Blinds blinds) {
        managePosition(DEALER, 0);
        managePosition(SMALL_BLIND, blinds.getSmall());
        managePosition(BIG_BLIND, blinds.getBig());
    }

    private void managePosition(Position position, int blind) {
        RoundPlayer player = round.getRoundPlayers().pollFirst();
        player.setPlayerPosition(position);
        player.chargeBlind(blind);
        round.getRoundPlayers().addLast(player);
    }

    void pickWinner(UUID playerId) {
        round.getRoundPlayers().stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .ifPresent(player -> player.winMoney(calculateTotalPot()));
        finishRound();
    }

    void updatePlayersBalance(UpdatePlayerBalanceRequest updateBalances) {
        round.getRoundPlayers().forEach(player -> player.winMoney(
                updateBalances.getPlayerMoney().stream()
                        .filter(updateBalance -> updateBalance.getPlayerId().equals(player.getId().toString()))
                        .findFirst()
                        .orElse(new PlayerMoney())
                        .getMoney()
        ));
        finishRound();
    }

    void finishRound() {
        final Collection<RoundPlayer> players = round.getRoundPlayers();
        gamePlayerService.updateAfterRound(players);
        players.forEach(RoundPlayer::clearBids);
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, getRoundPlayers()));
    }

    void preparePlayersForNextStage() {
        round.getRoundPlayers().forEach(RoundPlayer::prepareForNextStage);
        putProperPlayerOnTop();
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, getRoundPlayers()));
    }

    void showRoundPlayersSummary() {
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, getRoundPlayers(), true));
    }

    void bid(int amount) {
        RoundPlayer player = round.getRoundPlayers().pollFirst();
        player.bid(amount);
        round.getRoundPlayers().addLast(player);
    }

    void allIn() {
        RoundPlayer player = round.getRoundPlayers().pollFirst();
        player.allIn();
        clearPlayersInGameMadeMoveInStage();
        round.getRoundPlayers().addLast(player);
    }

    private void clearPlayersInGameMadeMoveInStage() {
        round.getRoundPlayers().stream()
                .filter(RoundPlayer::isInGame)
                .forEach(player -> player.setMadeMoveInStage(false));
    }

    void fold() {
        RoundPlayer player = round.getRoundPlayers().pollFirst();
        player.fold();
        round.getRoundPlayers().addLast(player);
    }

    boolean giveTurnToCurrentPlayer() {
        if (isAnyPlayerInGame()) {
            RoundPlayer player = round.getRoundPlayers().getFirst();
            if (player.isInGame()) {
                player.setHasTurn(true);
            } else {
                round.getRoundPlayers().addLast(round.getRoundPlayers().pollFirst());
                giveTurnToCurrentPlayer();
            }
            publisherGlobal.publishEvent(new RoundPlayersChanged(this, getRoundPlayers()));
            return true;
        }
        return false;
    }

    private void putProperPlayerOnTop() {
        if (!isPlayerOnSmallBlind()) {
            round.getRoundPlayers().addLast(round.getRoundPlayers().pollFirst());
            putProperPlayerOnTop();
        } else {
            getFirstPlayerInGame();
        }
    }

    private boolean isPlayerOnSmallBlind() {
        return round.getRoundPlayers().getFirst().getPlayerPosition().equals(SMALL_BLIND);
    }

    RoundPlayer getFirstPlayerInGame() {
        RoundPlayer player = round.getRoundPlayers().getFirst();
        if (player.isInGame()) {
            player.setHasTurn(true);
        } else {
            round.getRoundPlayers().addLast(round.getRoundPlayers().pollFirst());
            getFirstPlayerInGame();
        }
        return player;
    }

    int calculateTotalPot() {
        return round.getRoundPlayers().stream()
                .mapToInt(RoundPlayer::getRoundBid)
                .sum();
    }

    boolean isAnyPlayerInGame() {
        return round.getRoundPlayers().stream()
                .anyMatch(RoundPlayer::isInGame);
    }

    boolean playersBidsAreEqual() {
        return round.getRoundPlayers().stream()
                .filter(RoundPlayer::isInGame)
                .map(RoundPlayer::getTurnBid)
                .distinct()
                .count() == 1;
    }

    public void removeRoundPlayer(UUID id) {
        round.addPlayers(round.getRoundPlayers().stream()
                .filter(player -> !player.getId().equals(id))
                .collect(Collectors.toCollection(ArrayDeque::new)));
        gamePlayerService.removePlayer(id);
        publisherGlobal.publishEvent(new RoundPlayersChanged(this, getRoundPlayers()));
    }

    public boolean allHadTurnInStage() {
        return round.getRoundPlayers().stream().allMatch(RoundPlayer::madeMoveInStage);
    }

    Deque<RoundPlayer> getRoundPlayers() {
        return round.getRoundPlayers();
    }
}
