package app.domain.event;

import app.domain.game.Blinds;
import org.springframework.context.ApplicationEvent;

public class GameChanged extends ApplicationEvent {

    private final Blinds blinds;
    private final long gameTimeStamp;

    public GameChanged(final Object source, final Blinds blinds, long gameTimeStamp) {
        super(source);
        this.blinds = blinds;
        this.gameTimeStamp = gameTimeStamp;
    }

    public Blinds getBlinds() {
        return blinds;
    }

    public long getGameTimeStamp() {
        return gameTimeStamp;
    }
}
