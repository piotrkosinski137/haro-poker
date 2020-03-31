package app.domain.player;

import java.util.UUID;

class PlayerNotFound extends RuntimeException {

  public PlayerNotFound(UUID playerId) {
    super(String.format("Player with id %s not found", playerId.toString()));
  }
}
