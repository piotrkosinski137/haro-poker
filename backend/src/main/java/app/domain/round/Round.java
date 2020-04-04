package app.domain.round;

import app.domain.card.Card;

import java.util.*;

import static app.domain.round.RoundStage.*;

class Round {

    private Set<Card> tableCards;
    private Deque<RoundPlayer> roundPlayers;
    private RoundStage stage;
    private int totalPot;  //we will see

    public Round() {
        tableCards = new HashSet<>();
        roundPlayers = new ArrayDeque<>();
        stage = RoundStage.INIT;
    }

    public void addRoundPlayers(Collection<RoundPlayer> roundPlayers) {
        this.roundPlayers.addAll(roundPlayers);
    }

    //nextPlayer

    public Set<Card> getTableCards() {
        return tableCards;
    }

    public int getPot() {
        return totalPot;
    }

    public RoundStage getRoundStage() {
        return stage;
    }

    public void changeRoundStage() {
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
            case RIVER:
                stage = INIT; // to consider
                break;
            default:
                break;
        }
    }
}
