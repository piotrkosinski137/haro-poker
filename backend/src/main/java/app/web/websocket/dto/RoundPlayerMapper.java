package app.web.websocket.dto;

import app.domain.round.RoundPlayer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class RoundPlayerMapper {

    private ModelMapper mapper = EntityMapper.create();

    public RoundPlayerDto mapToDto(RoundPlayer roundPlayer) {
        return mapper.map(roundPlayer, RoundPlayerDto.class);
    }

    public Collection<RoundPlayerDto> mapToDtos(Collection<RoundPlayer> roundPlayers) {
        return roundPlayers.stream().map(this::mapToDto)
                .collect(Collectors.toSet());
    }
}
