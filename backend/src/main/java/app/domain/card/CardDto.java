package app.domain.card;

import java.util.Collection;
import java.util.stream.Collectors;

public class CardDto {
    private String rank;
    private String suit;

    private CardDto() {
    }

    public static CardDto fromCard(final Card card) {
        final CardDto cardDto = new CardDto();
        cardDto.setRank(card.getRank().getValue());
        cardDto.setSuit(card.getSuit().getValue());
        return cardDto;
    }

    public static Collection<CardDto> fromCardsCollection(final Collection<Card> cards) {
        return cards.stream().map(CardDto::fromCard)
                .collect(Collectors.toSet());
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }
}
