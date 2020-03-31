package app.domain.player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/*
 * Responsible for query operations (readonly/idempotent) - approach to consider
 * */
@Service
public class PlayerQueryService {

  private final PlayerRepository playerRepository;

  public PlayerQueryService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public Set<Player> findAll() {
    return playerRepository.findAll();
  }

  public Set<UUID> findAllIds() {
    return playerRepository.findAll().stream()
        .map(Player::getId)
        .collect(Collectors.toSet());
  }

  public Optional<Player> findById(UUID playerId) {
    return playerRepository.findById(playerId);
  }
}
