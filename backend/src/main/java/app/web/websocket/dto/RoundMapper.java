package app.web.websocket.dto;

import app.domain.card.Rank;
import app.domain.card.Suit;
import app.domain.round.Round;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoundMapper { //TODO!

    private ModelMapper mapper = new ModelMapper();

    public RoundMapper() {
        mapper.addConverter(convertRankToString());
        mapper.addConverter(convertSuitToString());
    }

    public RoundDto mapToDto(Round round) {
        return mapper.map(round, RoundDto.class);
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
