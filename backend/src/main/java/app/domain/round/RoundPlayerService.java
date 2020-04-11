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

    private final Deque<RoundPlayer> roundPlayers;

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

    void chargeBlinds(final Blinds blinds) { //TODO think about this
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
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .ifPresent(player -> player.winMoney(calculateTotalPot()));
    }

    void bid(int amount) {
        RoundPlayer player = roundPlayers.pollFirst();
        player.bid(amount);
        roundPlayers.addLast(player);
    }

    // TODO make proper checks to avoid StackOverflowException
    void setNextPlayer() {
        RoundPlayer player = roundPlayers.getFirst();
        if (!player.isInGame() || player.hasNoFunds()) {
            roundPlayers.addLast(roundPlayers.pollFirst());
            setNextPlayer();
        } else {
            player.setHasTurn(true);
        }
    }

    void fold() {
        RoundPlayer player = roundPlayers.pollFirst();
        player.fold();
        roundPlayers.addLast(player);
    }

    int calculateTotalPot() {
        return roundPlayers.stream()
                .mapToInt(RoundPlayer::getRoundBid)
                .sum();
    }

    boolean playersBidsAreEqual() {
        return roundPlayers.stream()
                .filter(RoundPlayer::isInGame)
                .map(RoundPlayer::getTurnBid)
                .distinct()
                .count() == 1;
    }

    public void setCurrentPlayer() {
        roundPlayers.getFirst().hasTurn(true);
    }
}
