package app.domain.event;

import app.domain.card.Card;
import app.domain.player.GamePlayer;
import org.springframework.context.ApplicationEvent;

import java.util.Collection;

public class TableCardsChanged extends ApplicationEvent {

    private Collection<Card> cards;

    public TableCardsChanged(Object source, Collection<Card> cards) {
        super(source);
        this.cards = cards;
    }

    public Collection<Card> getCards() {
        return cards;
    }
}
