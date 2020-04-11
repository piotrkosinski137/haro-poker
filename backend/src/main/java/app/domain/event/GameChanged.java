package app.domain.event;

import app.domain.game.Blinds;
import org.springframework.context.ApplicationEvent;

public class GameChanged extends ApplicationEvent {

    private Blinds blinds;

    public GameChanged(Object source, Blinds blinds) {
        super(source);
        this.blinds = blinds;
    }

    public Blinds getBlinds() {
        return blinds;
    }
}
