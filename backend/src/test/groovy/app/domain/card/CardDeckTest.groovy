package app.domain.card

import spock.lang.Specification

class CardDeckTest extends Specification{

    def 'should create new card deck' () {
        when:
        def cardDeck = CardDeck.getNewShuffledDeck();

        then: ' with 52 cards'
        cardDeck.getDeck().size() == 52
    }

    def 'should reset card deck' () {
        given:
        def cardDeck =CardDeck.getNewShuffledDeck();
        cardDeck.getDeck().poll();
        cardDeck.getDeck().poll();

        when:
        cardDeck = CardDeck.getNewShuffledDeck();

        then: 'and return new deck with 52 cards'
        cardDeck.getDeck().size() == 52
    }

    def 'should take existing card deck' () {
        given: 'and take 2 cards from deck'
        def cardDeck =CardDeck.getNewShuffledDeck();
        cardDeck.getDeck().poll();
        cardDeck.getDeck().poll();

        when:
        cardDeck = CardDeck.getExistingDeck();

        then: 'then return card deck with 50 cards'
        cardDeck.getDeck().size() == 50
    }

}


