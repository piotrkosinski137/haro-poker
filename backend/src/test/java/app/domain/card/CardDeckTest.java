package app.domain.card;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

class CardDeckTest {

	@Test
	void newShuffledDeckWithAllCards() {
		//when
		final CardDeck cardDeck = CardDeck.getNewShuffledDeck();

		//then
		assertThat(cardDeck.getDeck().size(), is(52));
	}

	@Test
	void shouldReturnNewDeckInstance() {
		//given
		final CardDeck cardDeck = CardDeck.getNewShuffledDeck();
		cardDeck.getDeck().poll();
		cardDeck.getDeck().poll();

		//when
		final CardDeck newCardDeck = CardDeck.getNewShuffledDeck();

		//then
		assertThat(newCardDeck.getDeck().size(), is(52));
	}

	@Test
	void shouldReturnExistingInstance() {
		//given
		final CardDeck cardDeck = CardDeck.getNewShuffledDeck();
		cardDeck.getDeck().poll();
		cardDeck.getDeck().poll();

		//when
		final CardDeck newCardDeck = CardDeck.getExistingDeck();

		//then
		assertThat(newCardDeck.getDeck().size(), is(50));
	}
}