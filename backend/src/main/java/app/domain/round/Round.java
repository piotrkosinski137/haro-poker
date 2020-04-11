package app.domain.round;

import static app.domain.round.RoundStage.FLOP;
import static app.domain.round.RoundStage.RIVER;
import static app.domain.round.RoundStage.TURN;

import app.domain.card.Card;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Round {

    private final Set<Card> tableCards;
    private RoundStage stage;

    Round() {
        tableCards = new HashSet<>();
        stage = RoundStage.INIT;
    }

    public Set<Card> getTableCards() {
        return Collections.unmodifiableSet(tableCards);
    }

    void putCardsOnTable(final Set<Card> cards) {
        tableCards.addAll(cards);
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
}
