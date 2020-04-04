package app.domain.card;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardDeckService {

    public void shuffleNewDeck() {
        CardDeck.getNewShuffledDeck();
    }

    public Set<Card> getCards(final int amount) {
        final CardDeck cardDeck = CardDeck.getExistingDeck();
        return cardDeck.getDeck().stream().limit(amount).collect(Collectors.toSet());
    }
}
