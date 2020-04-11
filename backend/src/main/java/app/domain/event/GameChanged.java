package app.domain.event;

import app.domain.game.Blinds;
import org.springframework.context.ApplicationEvent;

public class GameChanged extends ApplicationEvent {

    private final Blinds blinds;
    private final long timeStamp;

    public GameChanged(final Object source, final Blinds blinds, long timeStamp) {
        super(source);
        this.blinds = blinds;
        this.timeStamp = timeStamp;
    }

    public Blinds getBlinds() {
        return blinds;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
