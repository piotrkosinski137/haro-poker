package app.web.websocket.dto;

import app.domain.round.Round;
import app.domain.round.RoundDto;
import org.springframework.stereotype.Component;

@Component
public class RoundMapper {

    public RoundDto mapToDto(Round round) {
        return new RoundDto(
                round.getRoundStage().name(),
                round.getTableCards());
    }
}
