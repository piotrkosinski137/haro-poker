package app.domain.game.exceptions;

import java.util.UUID;

public class GamePlayerNotFound extends RuntimeException {
    public GamePlayerNotFound(UUID id) {
        super(String.format("GamePlayer %s doesn't exist", id.toString()));
    }
}
