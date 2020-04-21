package app.domain.round;

import app.domain.card.CardDto;
import java.util.Collection;
import java.util.List;

public class RoundDto {
    private Collection<CardDto> cards;
    private String stage;

    RoundDto(String stage, Collection<CardDto> cards) {
        this.cards = cards;
        this.stage = stage;
    }

    public Collection<CardDto> getCards() {
        return cards;
    }

    public void setCards(List<CardDto> cards) {
        this.cards = cards;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}
