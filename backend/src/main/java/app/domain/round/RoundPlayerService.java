package app.domain.round;

import static app.domain.round.Position.BIG_BLIND;
import static app.domain.round.Position.DEALER;
import static app.domain.round.Position.SMALL_BLIND;

import app.domain.game.Blinds;
import app.domain.player.GamePlayer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;
import java.util.stream.Collectors;

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
        player.bid(blind);
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
        if (player.getBalance() > amount) {
            player.bid(amount);
            roundPlayers.addLast(player);
        } else {
            allIn(player);
        }
    }

    void allIn(RoundPlayer player) {
        player.allIn();
        roundPlayers.addLast(player);
    }

    void fold() {
        RoundPlayer player = roundPlayers.pollFirst();
        player.fold();
        roundPlayers.addLast(player);
    }

    void giveTurnToCurrentPlayer() {
        RoundPlayer player = roundPlayers.getFirst();
        if (!player.isInGame()) {
            roundPlayers.addLast(roundPlayers.pollFirst());
            giveTurnToCurrentPlayer();
        } else {
            player.setHasTurn(true);
        }
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

    boolean isPlayerOnBigBlind() {
        return roundPlayers.getFirst().getPlayerPosition().equals(BIG_BLIND);
    }

    boolean isPlayerBidEqualsBigBlind() {
        return roundPlayers.getFirst().getTurnBid() == 200; //TODO
    }

    private void getFirstPlayerInGame() {
        RoundPlayer player = roundPlayers.getFirst();
        if (player.isInGame()) {
            player.setHasTurn(true);
        } else {
            roundPlayers.addLast(roundPlayers.pollFirst());
            getFirstPlayerInGame();
        }
    }

    int calculateTotalPot() {
        return roundPlayers.stream()
                .mapToInt(RoundPlayer::getRoundBid)
                .sum();
    }

    int calculateTurnPot() {
        return roundPlayers.stream()
                .mapToInt(RoundPlayer::getTurnBid)
                .sum();
    }

    boolean playersBidsAreEqual() {
        return roundPlayers.stream()
                .filter(RoundPlayer::isInGame)
                .map(RoundPlayer::getTurnBid)
                .distinct()
                .count() == 1;
    }

    void removeRoundPlayer(UUID id) {
        roundPlayers = roundPlayers.stream()
                .filter(player -> !player.getId().equals(id))
                .collect(Collectors.toCollection(ArrayDeque::new));
    }
}
