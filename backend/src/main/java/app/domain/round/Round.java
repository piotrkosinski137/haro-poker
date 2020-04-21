package app.domain.round;

import static app.domain.round.RoundStage.FINISHED;
import static app.domain.round.RoundStage.FLOP;
import static app.domain.round.RoundStage.INIT;
import static app.domain.round.RoundStage.RIVER;
import static app.domain.round.RoundStage.TURN;

import app.domain.card.CardDto;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
class Round {

    private Set<CardDto> tableCards;
    private RoundStage stage;
    private Deque<RoundPlayer> roundPlayers;

    Round() {
        tableCards = new HashSet<>();
        stage = RoundStage.NOT_STARTED;
        roundPlayers = new ArrayDeque<>();
    }

    Set<CardDto> getTableCards() {
        return Collections.unmodifiableSet(tableCards);
    }

    void putCardsOnTable(final Set<CardDto> cards) {
        tableCards.addAll(cards);
    }

    RoundStage getRoundStage() {
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

    void addPlayers(Deque<RoundPlayer> players) {
        roundPlayers = players;
    }

    public Deque<RoundPlayer> getRoundPlayers() {
        return roundPlayers;
    }
}
