package app.domain.exception;

import java.util.UUID;

public class PlayerNotFound extends RuntimeException {

  public PlayerNotFound(UUID playerId) {
    super(String.format("Player with id %s not found", playerId.toString()));
  }
}
