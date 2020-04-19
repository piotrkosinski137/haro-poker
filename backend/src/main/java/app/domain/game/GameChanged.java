package app.domain.game;

import org.springframework.context.ApplicationEvent;

public class GameChanged extends ApplicationEvent {

    private final GameDto gameDto;

    public GameChanged(final Object source, final GameDto gameDto) {
        super(source);
        this.gameDto = gameDto;
    }

    public GameDto getGameDto() {
        return gameDto;
    }
}
