package app.domain.event;

import app.domain.game.Blinds;
import org.springframework.context.ApplicationEvent;

public class GameChanged extends ApplicationEvent {

    private final Blinds blinds;

    public GameChanged(final Object source, final Blinds blinds) {
        super(source);
        this.blinds = blinds;
    }

    public Blinds getBlinds() {
        return blinds;
    }
}
