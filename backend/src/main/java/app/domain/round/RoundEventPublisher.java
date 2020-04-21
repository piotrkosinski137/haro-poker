package app.domain.round;

import java.util.Collection;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
class RoundEventPublisher {

    private final ApplicationEventPublisher publisher;

    public RoundEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishRoundEvent(RoundDto roundDto) {
        publisher.publishEvent(new RoundChanged(this, roundDto));
    }

    public void publishRoundPlayerEvent(Collection<RoundPlayerDto> players) {
        //publisher.publishEvent(new RoundPlayersChanged(this, players));
    }

    public void publishRoundPlayerWithCardsEvent(Collection<RoundPlayerDto> players) {
        //publisher.publishEvent(new RoundPlayersChanged(this, players));
    }
}
