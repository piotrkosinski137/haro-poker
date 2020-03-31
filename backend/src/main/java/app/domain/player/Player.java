package app.domain.player;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

class Player {

  private UUID id;
  private String name;
  private BigDecimal balance;

  public Player(String name) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.balance = new BigDecimal(10000);
  }

  public void bet(BigDecimal amount) {
    balance = balance.subtract(amount);
  }

  public void earnMoney(BigDecimal amount) {
    balance = balance.add(amount);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Player player = (Player) o;
    return id.equals(player.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
