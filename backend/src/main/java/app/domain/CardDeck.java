package app.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class CardDeck {

  private final Deque<Card> cardsDeck = new ArrayDeque<>();

  public Set<Card> getCards(int amount) {
    Set<Card> cards = new HashSet<>();
    for (int i = 0; i < amount; i++) {
      cards.add(cardsDeck.pollLast());
    }
    return cards;
  }
}
