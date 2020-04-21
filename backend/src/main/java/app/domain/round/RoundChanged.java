package app.domain.round;

import org.springframework.context.ApplicationEvent;

public class RoundChanged extends ApplicationEvent {

    private final RoundDto round;

    public RoundChanged(final Object source, final RoundDto round) {
        super(source);
        this.round = round;
    }

    public RoundDto getRound() {
        return round;
    }
}
