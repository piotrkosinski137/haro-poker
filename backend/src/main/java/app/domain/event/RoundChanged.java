package app.domain.event;

import app.domain.round.Round;
import org.springframework.context.ApplicationEvent;

public class RoundChanged extends ApplicationEvent {

    private final Round round;

    public RoundChanged(final Object source, final Round round) {
        super(source);
        this.round = round;
    }

    public Round getRound() {
        return round;
    }
}
