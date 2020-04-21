package app.domain.round;

import app.domain.card.CardDeckService;
import app.domain.round.exception.RoundNotStarted;
import org.springframework.stereotype.Service;

@Service
class RoundServiceImpl implements RoundService {

    private final CardDeckService cardDeckService;
    private final RoundEventPublisher publisher;
    private final Round round;

    public RoundServiceImpl(final CardDeckService cardDeckService, RoundEventPublisher publisher, Round round) {
        this.cardDeckService = cardDeckService;
        this.publisher = publisher;
        this.round = round;
    }

    @Override
    public void startRound() {
        cardDeckService.shuffleNewDeck();
        round.start();
        publisher.publishRoundEvent(getRound());
    }

    @Override
    public void startNextStage() {
        changeRoundStage();
        putCardsOnTable();
        publisher.publishRoundEvent(getRound());
    }

    @Override
    public void changeRoundStage() {
        if (round.getRoundStage() == RoundStage.NOT_STARTED) {
            throw new RoundNotStarted();
        }
        round.changeRoundStage();
    }

    @Override
    public RoundDto getRound() {
        return new RoundDto(round.getRoundStage().name(), round.getTableCards());
    }

    private void putCardsOnTable() {
        round.putCardsOnTable(cardDeckService.getCards(round.getRoundStage().getCardAmount()));
    }
}
