package app.domain.round;

public interface RoundService {

    void startRound();

    void startNextStage();

    void changeRoundStage();

    RoundDto getRound();
}
