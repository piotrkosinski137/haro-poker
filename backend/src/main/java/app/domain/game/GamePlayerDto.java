package app.domain.game;

import java.util.Collection;
import java.util.stream.Collectors;

public class GamePlayerDto {

    private String id;
    private Integer tableNumber;
    private String name;
    private boolean active;
    private int balance;

    private GamePlayerDto() {
    }

    public static GamePlayerDto fromGamePlayer(final GamePlayer gamePlayer) {
        final GamePlayerDto gamePlayerDto = new GamePlayerDto();
        gamePlayerDto.setId(gamePlayer.getId().toString());
        gamePlayerDto.setTableNumber(gamePlayer.getTableNumber());
        gamePlayerDto.setName(gamePlayer.getName());
        gamePlayerDto.setActive(gamePlayer.isActive());
        gamePlayerDto.setBalance(gamePlayer.getBalance());
        return gamePlayerDto;
    }

    public static Collection<GamePlayerDto> fromGamePlayersCollection(final Collection<GamePlayer> gamePlayers) {
        return gamePlayers.stream().map(GamePlayerDto::fromGamePlayer)
                .collect(Collectors.toSet());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
