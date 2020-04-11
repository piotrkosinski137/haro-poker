package app.web.websocket.dto;

import app.domain.player.GamePlayer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class GamePlayerMapper {

    private ModelMapper mapper = EntityMapper.create();

    public GamePlayerDto mapToDto(GamePlayer gamePlayer) {
        return mapper.map(gamePlayer, GamePlayerDto.class);
    }

    public Collection<GamePlayerDto> mapToDtos(Collection<GamePlayer> gamePlayers) {
        return gamePlayers.stream().map(this::mapToDto)
                .collect(Collectors.toSet());
    }
}
