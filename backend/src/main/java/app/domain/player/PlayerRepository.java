package app.domain.player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
class PlayerRepository {

  private Set<Player> players = new HashSet<>();

  public Optional<Player> findById(UUID playerId) {
    return players.stream()
        .filter(player -> player.getId().equals(playerId))
        .findFirst();
  }

  public boolean existsByName(String name) {
    return players.stream()
        .anyMatch(player -> player.getName().equals(name));
  }

  public void save(Player player) {
    players.add(player);
  }

  public Set<Player> findAll() {
    return Collections.unmodifiableSet(players);
  }

  public void remove(UUID playerId) {
    players.removeIf(player -> player.getId().equals(playerId));
  }
}
