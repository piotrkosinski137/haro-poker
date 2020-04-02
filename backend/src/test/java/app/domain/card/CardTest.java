package app.domain.card;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.Test;

class CardTest {

	private Card firstCard;
	private Card secondCard;

	@Test
	void compareTwoTheSameCards() {
		//given
		firstCard = new Card(Rank.SIX, Suit.CLUBS);
		secondCard = new Card(Rank.SIX, Suit.CLUBS);

		//when
		final int compareResult = firstCard.compareTo(secondCard);

		//then
		assertThat(compareResult, is(0));
	}

	@Test
	void compareTwoTheSameCardsWithRankValueHigherThen10() {
		//given
		firstCard = new Card(Rank.ACE, Suit.SPADES);
		secondCard = new Card(Rank.ACE, Suit.SPADES);

		//when
		final int compareResult = firstCard.compareTo(secondCard);

		//then
		assertThat(compareResult, is(0));
	}

	@Test
	void compareTwoDifferentCards() {
		//given
		firstCard = new Card(Rank.SIX, Suit.CLUBS);
		secondCard = new Card(Rank.JACK, Suit.DIAMONDS);

		//when
		final int compareResult = firstCard.compareTo(secondCard);

		//then
		assertThat(compareResult, not(0));
	}

	@Test
	void compareTwoCardsWithDifferentSuit() {
		//given
		firstCard = new Card(Rank.SIX, Suit.CLUBS);
		secondCard = new Card(Rank.SIX, Suit.DIAMONDS);

		//when
		final int compareResult = firstCard.compareTo(secondCard);

		//then
		assertThat(compareResult, not(0));
	}

	@Test
	void compareTwoCardsWithDifferentRank() {
		//given
		firstCard = new Card(Rank.QUEEN, Suit.DIAMONDS);
		secondCard = new Card(Rank.SIX, Suit.DIAMONDS);

		//when
		final int compareResult = firstCard.compareTo(secondCard);

		//then
		assertThat(compareResult, not(0));
	}
}
