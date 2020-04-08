package app.domain.game;

public class GameIsFull extends RuntimeException {

    GameIsFull() {
        super("Game is full");
    }
}
