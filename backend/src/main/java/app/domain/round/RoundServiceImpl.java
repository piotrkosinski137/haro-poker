package app.domain.round;

import app.domain.card.CardDeckService;
import app.domain.round.exception.RoundNotStarted;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RoundServiceImpl {

    private final CardDeckService cardDeckService;
    private final ApplicationEventPublisher publisherGlobal;
    private final Round round;

    public RoundServiceImpl(final CardDeckService cardDeckService,
            ApplicationEventPublisher publisherGlobal, Round round) {
        this.cardDeckService = cardDeckService;
        this.publisherGlobal = publisherGlobal;
        this.round = round;
    }

    public void startRound() {
        cardDeckService.shuffleNewDeck();
        round.start();
        publisherGlobal.publishEvent(new RoundChanged(this, getRound()));
    }

    void startNextStage() {
        changeRoundStage();
        putCardsOnTable();
        publisherGlobal.publishEvent(new RoundChanged(this, getRound()));
    }

    void changeRoundStage() {
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        round.changeRoundStage();
    }

    private void putCardsOnTable() {
        round.putCardsOnTable(cardDeckService.getCards(round.getRoundStage().getCardAmount()));
    }

    public RoundDto getRound() {
        return new RoundDto(round.getRoundStage().name(), round.getTableCards());
    }
}
