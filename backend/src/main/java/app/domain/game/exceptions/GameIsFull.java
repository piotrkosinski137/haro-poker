package app.domain.game.exceptions;

public class GameIsFull extends RuntimeException {

    public GameIsFull() {
        super("Game is full");
    }
}
