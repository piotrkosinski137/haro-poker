package app.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class PlayersSocketController {

    private Set<PlayerDTO> players = new HashSet<>();

    private final SimpMessagingTemplate template;

    public PlayersSocketController(SimpMessagingTemplate template) {
        this.template = template;
        players.add(new PlayerDTO(2, "Dealer Toms", 2300, 0, 0, false, 0, false, true));
        players.add(new PlayerDTO(3, "Small Dziku", 11130, 100, 100, false, 1, false, true));
        players.add(new PlayerDTO(4, "Big Magier", 7330, 200, 200, false, 2, false, true));
        players.add(new PlayerDTO(6, "Current Demon", 8110, 0, 0, true, 3, false, true));
    }

    @MessageMapping("/players/add")
    public void addPlayer(@Payload String playerName) {
        players.add(new PlayerDTO(calculateId(), playerName.replace("\"",""), 10000, 0, 0, false, 3, false, true));
        template.convertAndSend("/topic/players", players);
    }

    @SubscribeMapping("/topic/players")
    public Set<PlayerDTO> onSubscribe() {
        return players;
    }

    private int calculateId() {
        for (int i = 1; i <= 7; i++) {
            int finalI = i; // TODO make it nicer
            if (players.stream().noneMatch(player -> player.getId() == finalI)) {
                return i;
            }
        }
        throw new RuntimeException("Table is full"); // TODO @ControllerAdvice
    }
}

class PlayerDTO {
    private String name;
    private int balance;
    private int id;
    private int turnBid;
    private int roundBid;
    private boolean hasTurn;
    private int playerPosition;
    private boolean hasFolded;
    private boolean active;

    private PlayerDTO() {
    }

    public PlayerDTO(int id, String name, int balance, int turnBid, int roundBid, boolean hasTurn, int playerPosition, boolean hasFolded, boolean active) {
        this.name = name;
        this.balance = balance;
        this.id = id;
        this.turnBid = turnBid;
        this.roundBid = roundBid;
        this.hasTurn = hasTurn;
        this.playerPosition = playerPosition;
        this.hasFolded = hasFolded;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTurnBid() {
        return turnBid;
    }

    public void setTurnBid(int turnBid) {
        this.turnBid = turnBid;
    }

    public int getRoundBid() {
        return roundBid;
    }

    public void setRoundBid(int roundBid) {
        this.roundBid = roundBid;
    }

    public boolean isHasTurn() {
        return hasTurn;
    }

    public void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

    public boolean isHasFolded() {
        return hasFolded;
    }

    public void setHasFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }

    public boolean isActive() {
        return active;
    }

    public void setIsActive(boolean isActive) {
        this.active = isActive;
    }
}
