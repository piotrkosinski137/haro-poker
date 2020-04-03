package app.domain.card;

import java.util.Objects;

public class Card implements Comparable<Card> {

	private final Rank rank;
	private final Suit suit;

	Card(final Rank rank, final Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Rank getRank() {
		return this.rank;
	}

	public Suit getSuit() {
		return this.suit;
	}

	private String getCardValue() {
		return rank.getValue().concat(suit.getValue());
	}

	public int compareTo(final Card o) {
		return this.getCardValue().compareTo(o.getCardValue());
	}

	@Override
	public boolean equals(final Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		final Card card = (Card) o;
		return rank == card.rank &&
				suit == card.suit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.rank, this.suit);
	}
}