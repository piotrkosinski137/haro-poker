package app.domain.card;

import java.util.*;

class CardDeck {

    private static final List<Card> CACHE_DECK = initCache();
    private static CardDeck INSTANCE;

    private final Deque<Card> cardsDeck;

    private CardDeck() {
        this.cardsDeck = initDeck();
        Collections.shuffle(new ArrayList<>(cardsDeck));
    }

    static CardDeck getExistingDeck() {
        if (INSTANCE == null) {
            INSTANCE = new CardDeck();
        }
        return INSTANCE;
    }

    static CardDeck getNewShuffledDeck() {
        INSTANCE = new CardDeck();
        return INSTANCE;
    }

    private static List<Card> initCache() {
        final List<Card> cache = new ArrayList<>();
        for (final Suit suit : Suit.values()) {
            for (final Rank rank : Rank.values()) {
                cache.add(new Card(rank, suit));
            }
        }
        return Collections.unmodifiableList(cache);
    }

    Deque<Card> getDeck() {
        return cardsDeck;
    }

    private Deque<Card> initDeck() {
        return new ArrayDeque<>(CACHE_DECK);
    }
}





