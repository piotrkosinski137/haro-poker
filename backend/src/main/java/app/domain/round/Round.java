package app.domain.round;

import app.domain.card.Card;
import app.domain.game.Blinds;

import java.util.*;

import static app.domain.round.RoundStage.*;

class Round {

    private Set<Card> tableCards;
    private Deque<RoundPlayer> roundPlayers;
    private RoundStage stage;

    public Round() {
        tableCards = new HashSet<>();
        roundPlayers = new ArrayDeque<>();
        stage = RoundStage.INIT;
    }

    void addRoundPlayers(Collection<RoundPlayer> roundPlayers) {
        this.roundPlayers.addAll(roundPlayers);
    }

    void chargeBlinds(Blinds blinds) {
        //dealer gives 0 blind
        roundPlayers.addLast(roundPlayers.pollFirst());
        //small gives small blind
        bid(blinds.getSmall());
        //big gives big blind
        bid(blinds.getBig());
    }

    void bid(int amount) {
        RoundPlayer player = roundPlayers.pollFirst();
        player.bid(amount);
        roundPlayers.addLast(player);
        // trigger websocket to see that next player has changed
        // trigger websocket to see that totalPot has changed
    }

    void fold() {
        RoundPlayer player = roundPlayers.pollFirst();
        player.fold();
        roundPlayers.addLast(player);
    }

    void nextStage() {
        changeRoundStage();
        roundPlayers.forEach(RoundPlayer::nextStage);
    }

    Deque<RoundPlayer> getRoundPlayers() {
        return roundPlayers;
    }

    boolean playersBidsAreEqual() {
        return roundPlayers.stream()
                .filter(RoundPlayer::isInGame)
                .map(RoundPlayer::getTurnBid)
                .distinct()
                .count() == 1;
    }

    void putCardsOnTable(Set<Card> cards) {
        tableCards.addAll(cards);
    }

    int calculateTotalPot() {
        return roundPlayers.stream()
                .mapToInt(RoundPlayer::getRoundBid)
                .sum();
    }

    RoundStage getRoundStage() {
        return stage;
    }

    void changeRoundStage() {
        switch (stage) {
            case INIT:
                stage = FLOP;
                break;
            case FLOP:
                stage = TURN;
                break;
            case TURN:
                stage = RIVER;
                break;
            default:
                break;
        }
    }

    void pickWinner(int playerId) {
        roundPlayers.stream()
                .filter(player -> player.getId() == playerId)
                .forEach(player -> player.winMoney(calculateTotalPot()));
    }
}
