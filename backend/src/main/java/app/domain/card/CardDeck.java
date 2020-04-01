package app.domain.card;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class CardDeck {

	private static final List<Card> CACHE_DECK = initCache();
	private static CardDeck INSTANCE;

	private final Deque<Card> cardsDeck;

	private CardDeck() {
		this.cardsDeck = initDeck();
		Collections.shuffle(new ArrayList<>(cardsDeck));
	}

	public static CardDeck getExistingDeck() {
		if(INSTANCE == null) {
			INSTANCE = new CardDeck();
		}
		return INSTANCE;
	}

	public static CardDeck getNewShuffledDeck() {
		INSTANCE = new CardDeck();
		return INSTANCE;
	}

	public Deque<Card> getDeck() {
		return cardsDeck;
	}

	private static List<Card> initCache() {
		final List<Card> cache = new ArrayList<>();
		for(final Suit suit : Suit.values()) {
			for(final Rank rank : Rank.values()) {
				cache.add(new Card(rank, suit));
			}
		}
		return Collections.unmodifiableList(cache);
	}

	private Deque<Card> initDeck() {
		return new ArrayDeque<>(CACHE_DECK);
	}
}





