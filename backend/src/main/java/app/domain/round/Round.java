package app.domain.round;

import static app.domain.round.RoundStage.FINISHED;
import static app.domain.round.RoundStage.FLOP;
import static app.domain.round.RoundStage.INIT;
import static app.domain.round.RoundStage.RIVER;
import static app.domain.round.RoundStage.TURN;

import app.domain.card.CardDto;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Round {

    private Set<CardDto> tableCards;
    private RoundStage stage;

    Round() {
        tableCards = new HashSet<>();
        stage = RoundStage.NOT_STARTED;
    }

    public Set<CardDto> getTableCards() {
        return Collections.unmodifiableSet(tableCards);
    }

    void putCardsOnTable(final Set<CardDto> cards) {
        tableCards.addAll(cards);
    }

    public RoundStage getRoundStage() {
        return stage;
    }

    void start() {
      stage = INIT;
      tableCards = new HashSet<>();
    }

    void changeRoundStage() {
        switch (stage) {
            case NOT_STARTED:
                stage = INIT;
                break;
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
                stage = FINISHED;
                break;
            default:
                break;
        }
    }
}
