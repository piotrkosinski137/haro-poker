package app.domain.round;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoundPlayerMapper {

    private ModelMapper mapper = EntityMapper.create();

    public RoundPlayerDto mapToDto(RoundPlayer roundPlayer) {
        //Tu nie wiem jak ladnie pominąć ten set z kartami, jak cos moge to przerobić na mapstracta albo
        // recznie po prostu sie to zrobi ale to po obiedzie albo wieczorkiem
        final RoundPlayerDto map = mapper.map(roundPlayer, RoundPlayerDto.class);
        map.setCardsInHand(new HashSet<>());
        return map;
    }

    public RoundPlayerDto mapToDtoWithCards(RoundPlayer roundPlayer) {
        return mapper.map(roundPlayer, RoundPlayerDto.class);
    }

    public Collection<RoundPlayerDto> mapToDtos(Collection<RoundPlayer> roundPlayers) {
        return roundPlayers.stream().map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    public Collection<RoundPlayerDto> mapToDtosWithCards(Collection<RoundPlayer> roundPlayers) {
        return roundPlayers.stream().map(this::mapToDtoWithCards)
                .collect(Collectors.toSet());
    }
}
