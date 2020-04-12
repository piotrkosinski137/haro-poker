package app.domain.round.exception;

public class PlayerNotFound extends RuntimeException {

    public PlayerNotFound() {
        super("Player not found");
    }

}
