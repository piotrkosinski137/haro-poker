package app.domain.event;

import app.domain.card.Card;
import java.util.Collection;
import org.springframework.context.ApplicationEvent;

public class TableCardsChanged extends ApplicationEvent {

    private final Collection<Card> cards;

    public TableCardsChanged(final Object source, final Collection<Card> cards) {
        super(source);
        this.cards = cards;
    }

    public Collection<Card> getCards() {
        return cards;
    }
}
