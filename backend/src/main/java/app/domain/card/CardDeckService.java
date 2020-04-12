package app.domain.card;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CardDeckService {

    public void shuffleNewDeck() {
        CardDeck.getNewShuffledDeck();
    }

    public Set<Card> getCards(final int amount) {
        Set<Card> cards = new HashSet<>();
        final CardDeck cardDeck = CardDeck.getExistingDeck();
        for (int i = 0; i < amount; i++) {
            cards.add(cardDeck.getDeck().pollFirst());
        }
        return cards;
    }
}
