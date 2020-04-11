package app.domain.round.exception;

public class RoundNotStarted extends RuntimeException {

    public RoundNotStarted() {
        super("Round is not started yet");
    }

}
