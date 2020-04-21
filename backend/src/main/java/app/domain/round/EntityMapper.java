package app.domain.round;

import java.util.UUID;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

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
        mapper.addConverter(convertPositionToString());
    }

    private static AbstractConverter<UUID, String> convertUUIDtoString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(UUID id) {
                return id.toString();
            }
        };
    }

    private static AbstractConverter<Position, String> convertPositionToString() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(Position position) {
                return position.name();
            }
        };
    }
}
