package app.domain.card;

import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class CardService {

  private CardDeck cardDeck;

  public CardService() {
    cardDeck = new CardDeck();
  }

  /*
   * returns given amount of cards
   * */
  public Set<Card> getCards(int amount) {
    return cardDeck.getCards(amount);
  }


  /*
   * Prepares full set of cards for new round
   * */
  public void prepareNewCards() {

  }
}
