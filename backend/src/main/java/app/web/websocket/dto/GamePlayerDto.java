package app.web.websocket.dto;

public class GamePlayerDto {
    private String id;
    private Integer tableNumber;
    private String name;
    private int playerPosition;
    private boolean active;

    private GamePlayerDto() {
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public boolean isActive() {
        return active;
    }

    public void setIsActive(boolean isActive) {
        this.active = isActive;
    }
}
