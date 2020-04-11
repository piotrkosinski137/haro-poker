package app.web.rest;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerRestController {

    private final GameService gameService;
    private final RoundService roundService;

    public PlayerRestController(GameService gameService, RoundService roundService) {
        this.gameService = gameService;
        this.roundService = roundService;
    }

    @PostMapping("/add")
    public AddPlayerResponse addPlayer(@RequestParam String playerName) {
        UUID playerId = gameService.joinToGame(playerName);
        return new AddPlayerResponse(playerId.toString());
    }

    @PostMapping("/{id}/cards")
    public void getCards(@PathVariable String id) {
        roundService.getPlayerCards(id);
    }

    @PostMapping("/{id}/activation-status")
    public void changePlayerActiveStatus(@PathVariable String id, @RequestParam boolean isActive) {
        gameService.changeActiveStatus(UUID.fromString(id), isActive);
    }

    //TODO consider if id needed for security reasons
    @PostMapping("/{id}/bid")
    public void bid(@PathVariable String id, @RequestParam int amount) {
        roundService.bid(amount);
    }

    //TODO consider if id needed for security reasons
    @PostMapping("/{id}/fold")
    public void fold(@PathVariable String id) {
        roundService.fold();
    }
}
