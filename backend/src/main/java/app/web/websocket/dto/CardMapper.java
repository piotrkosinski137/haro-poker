package app.web.websocket.dto;

import app.domain.card.Card;
import app.domain.card.Rank;
import app.domain.card.Suit;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CardMapper {

    private ModelMapper mapper = new ModelMapper();

    public CardMapper() {
        mapper.addConverter(convertRankToString());
        mapper.addConverter(convertSuitToString());
    }

    public CardDto mapToDto(Card card) {
        return mapper.map(card, CardDto.class);
    }

    public Collection<CardDto> mapToDtos(Collection<Card> cards) {
        return cards.stream().map(this::mapToDto)
                .collect(Collectors.toSet());
    }

    private AbstractConverter<Rank, String> convertRankToString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Rank rank) {
                return rank.name();
            }
        };
    }

    private AbstractConverter<Suit, String> convertSuitToString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Suit suit) {
                return suit.name();
            }
        };
    }
}
