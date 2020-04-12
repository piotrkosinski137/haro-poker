package app.web.websocket.dto;

import app.domain.card.Rank;
import app.domain.card.Suit;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import java.util.UUID;

public class EntityMapper {

    public static ModelMapper create() {
        ModelMapper mapper = new ModelMapper();
        setupMapper(mapper);
        return mapper;
    }

    private static void setupMapper(ModelMapper mapper) {
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        mapper.addConverter(convertUUIDtoString());
        mapper.addConverter(convertRankToString());
        mapper.addConverter(convertSuitToString());
    }

    private static AbstractConverter<UUID, String> convertUUIDtoString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(UUID id) {
                return id.toString();
            }
        };
    }

    private static AbstractConverter<Rank, String> convertRankToString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Rank rank) {
                return rank.getValue();
            }
        };
    }

    private static AbstractConverter<Suit, String> convertSuitToString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Suit suit) {
                return suit.getValue();
            }
        };
    }
}
