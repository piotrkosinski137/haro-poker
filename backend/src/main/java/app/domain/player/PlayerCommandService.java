package app.domain.player;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;

/*
 * Responsible for command operations (make action/change) - approach to consider
 * */
@Service
public class PlayerCommandService {

  private final PlayerRepository playerRepository;

  public PlayerCommandService(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public void bet(UUID playerId, BigDecimal amount) {
    playerRepository.findById(playerId)
        .orElseThrow(() -> new PlayerNotFound(playerId))
        .bet(amount);
  }

  public void earnMoney(UUID playerId, BigDecimal amount) {
    playerRepository.findById(playerId)
        .orElseThrow(() -> new PlayerNotFound(playerId))
        .earnMoney(amount);
  }

  public UUID addPlayer(String name) {
    if (nameAlreadyExist(name)) {
      name = addNumberToName(name);
    }
    Player player = new Player(name);
    playerRepository.save(player);
    return player.getId();
  }

  public void remove(UUID playerId) {
    playerRepository.remove(playerId);
  }

  private boolean nameAlreadyExist(String name) {
    return playerRepository.existsByName(name);
  }

  private String addNumberToName(String name) {
    int randomNumber = (int) (Math.random() * 1000);
    name += randomNumber;
    return name;
  }
}
