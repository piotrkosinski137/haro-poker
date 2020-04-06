package app.domain.card;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

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
