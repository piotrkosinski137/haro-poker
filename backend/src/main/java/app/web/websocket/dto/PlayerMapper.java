package app.web.websocket.dto;

import app.domain.player.Player;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PlayerMapper {

    private ModelMapper mapper = new ModelMapper();

    public PlayerMapper() {
        mapper.addConverter(convertUUIDtoString());
    }

    public PlayerDto mapToDto(Player player) {
        return mapper.map(player, PlayerDto.class);
    }

    public Collection<PlayerDto> mapToDtos(Collection<Player> players) {
        return players.stream().map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    private AbstractConverter<UUID, String> convertUUIDtoString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(UUID id) {
                return id.toString();
            }
        };
    }
}
