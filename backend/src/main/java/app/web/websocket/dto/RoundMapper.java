package app.web.websocket.dto;

import app.domain.round.Round;
import org.springframework.stereotype.Component;

@Component
public class RoundMapper {

    private final CardMapper cardMapper;

    public RoundMapper(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public RoundDto mapToDto(Round round) {
        return new RoundDto(
                round.getRoundStage().name(),
                cardMapper.mapToDtos(round.getTableCards()));
    }
}
