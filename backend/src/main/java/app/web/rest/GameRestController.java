package app.web.rest;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class GameRestController {

    private final GameService gameService;
    private final RoundService roundService;

    public GameRestController(GameService gameService, RoundService roundService) {
        this.gameService = gameService;
        this.roundService = roundService;
    }

    @PostMapping("/players/add")
    public AddPlayerResponse addPlayer(@RequestParam String playerName) {
        UUID playerId = gameService.joinToGame(playerName);
        return new AddPlayerResponse(playerId.toString());
    }

    @PostMapping("/game/start")
    public void startRound() {
        gameService.startGame();
    }

    @PostMapping("/game/nextStage")
    public void nextStage() {
        roundService.startNextStage();
    }

    @PutMapping("/game/blinds/update")
    public void updateBlinds(@RequestParam int small) {
        gameService.updateBlinds(small);
    }

    @PostMapping("/players/{id}/activation-status")
    public void changePlayerActiveStatus(@PathVariable String id, @RequestParam boolean isActive) {
        gameService.changeActiveStatus(UUID.fromString(id), isActive);
    }

    //TODO consider if id needed for security reasons
    @PostMapping("/players/{id}/bid")
    public void bid(@PathVariable String id, @RequestParam int amount) {
        roundService.bid(amount);
    }

    //TODO consider if id needed for security reasons
    @PostMapping("/players/{id}/fold")
    public void fold(@PathVariable String id) {
        roundService.fold();
    }
}
