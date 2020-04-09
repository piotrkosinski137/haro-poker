package app.domain.card


import spock.lang.Specification

class CardTest extends Specification {

    def firstCard = new Card(Rank.SIX, Suit.CLUBS);


    def 'should compare cards and cards are not the same'() {
        when:
        def compareResult = firstCard.compareTo(secondCard);

        then:
        compareResult != 0

        where:
        secondCard                        | _
        new Card(Rank.ACE, Suit.CLUBS)    | _
        new Card(Rank.SIX, Suit.DIAMONDS) | _
        new Card(Rank.JACK, Suit.HEARTS)  | _
    }

    def 'should compare cards and cards are the same'() {
        given:
        def secondCard = new Card(Rank.SIX, Suit.CLUBS);

        when:
        def compareResult = firstCard.compareTo(secondCard);

        then:
        compareResult == 0
    }

}

