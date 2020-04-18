package app.domain.round;

import app.domain.game.Blinds;
import app.domain.player.GamePlayer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;
import java.util.stream.Collectors;

import static app.domain.round.Position.BIG_BLIND;
import static app.domain.round.Position.DEALER;
import static app.domain.round.Position.SMALL_BLIND;

class RoundPlayerService {

    private Deque<RoundPlayer> roundPlayers;

    RoundPlayerService(final Deque<GamePlayer> gamePlayers) {
        this.roundPlayers = convertToRoundPlayers(gamePlayers);
    }

    Deque<RoundPlayer> getRoundPlayers() {
        return new ArrayDeque<>(roundPlayers);
    }

    private Deque<RoundPlayer> convertToRoundPlayers(final Deque<GamePlayer> gamePlayers) {
        return gamePlayers.stream()
                .map(player -> new RoundPlayer(player.getId(), player.getBalance(), player.getTableNumber()))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    void chargeBlinds(final Blinds blinds) {
        managePosition(DEALER, 0);
        managePosition(SMALL_BLIND, blinds.getSmall());
        managePosition(BIG_BLIND, blinds.getBig());
    }

    private void managePosition(Position position, int blind) {
        RoundPlayer player = roundPlayers.pollFirst();
        player.setPlayerPosition(position);
        player.chargeBlind(blind);
        roundPlayers.addLast(player);
    }

    void pickWinner(UUID playerId) {
        roundPlayers.stream()
                .filter(player -> player.getId().equals(playerId))
                .findFirst()
                .ifPresent(player -> player.winMoney(calculateTotalPot()));
    }

    void bid(int amount) {
        RoundPlayer player = roundPlayers.pollFirst();
        player.bid(amount);
        roundPlayers.addLast(player);
    }

    void allIn() {
        RoundPlayer player = roundPlayers.pollFirst();
        player.allIn();
        clearPlayersInGameMadeMoveInStage();
        roundPlayers.addLast(player);
    }

    private void clearPlayersInGameMadeMoveInStage() {
        roundPlayers.stream()
                .filter(RoundPlayer::isInGame)
                .forEach(player -> player.setMadeMoveInStage(false));
    }

    void fold() {
        RoundPlayer player = roundPlayers.pollFirst();
        player.fold();
        roundPlayers.addLast(player);
    }

    boolean giveTurnToCurrentPlayer() {
        if (isAnyPlayerInGame()) {
            RoundPlayer player = roundPlayers.getFirst();
            if (player.isInGame()) {
                player.setHasTurn(true);
            } else {
                roundPlayers.addLast(roundPlayers.pollFirst());
                giveTurnToCurrentPlayer();
            }
            return true;
        }
        return false;
    }

    void putProperPlayerOnTop() {
        if (!isPlayerOnSmallBlind()) {
            roundPlayers.addLast(roundPlayers.pollFirst());
            putProperPlayerOnTop();
        } else {
            getFirstPlayerInGame();
        }
    }

    boolean isPlayerOnSmallBlind() {
        return roundPlayers.getFirst().getPlayerPosition().equals(SMALL_BLIND);
    }

    RoundPlayer getFirstPlayerInGame() {
        RoundPlayer player = roundPlayers.getFirst();
        if (player.isInGame()) {
            player.setHasTurn(true);
        } else {
            roundPlayers.addLast(roundPlayers.pollFirst());
            getFirstPlayerInGame();
        }
        return player;
    }

    int calculateTotalPot() {
        return roundPlayers.stream()
                .mapToInt(RoundPlayer::getRoundBid)
                .sum();
    }

    boolean isAnyPlayerInGame() {
        return roundPlayers.stream()
                .anyMatch(RoundPlayer::isInGame);
    }

    boolean playersBidsAreEqual() {
        return roundPlayers.stream()
                .filter(RoundPlayer::isInGame)
                .map(RoundPlayer::getTurnBid)
                .distinct()
                .count() == 1;
    }

    public void removeRoundPlayer(UUID id) {
        roundPlayers = roundPlayers.stream()
                .filter(player -> !player.getId().equals(id))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public boolean allHadTurnInStage() {
        return roundPlayers.stream().allMatch(RoundPlayer::madeMoveInStage);
    }

    public void preparePlayersForNextStage() {
        roundPlayers.forEach(RoundPlayer::prepareForNextStage);
    }

}
