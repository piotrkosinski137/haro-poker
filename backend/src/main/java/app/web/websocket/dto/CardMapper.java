package app.web.websocket.dto;

import app.domain.card.Card;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CardMapper {

    private ModelMapper mapper = EntityMapper.create();

    public CardDto mapToDto(Card card) {
        return mapper.map(card, CardDto.class);
    }

    public Collection<CardDto> mapToDtos(Collection<Card> cards) {
        return cards.stream().map(this::mapToDto)
                .collect(Collectors.toSet());
    }
}
