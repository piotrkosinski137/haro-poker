package app.web.rest;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import app.web.websocket.dto.CardDto;
import app.web.websocket.dto.CardMapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/player")
public class PlayerRestController {

    private final GameService gameService;
    private final RoundService roundService;
    private final CardMapper cardMapper;

    public PlayerRestController(GameService gameService, RoundService roundService, CardMapper cardMapper) {
        this.gameService = gameService;
        this.roundService = roundService;
        this.cardMapper = cardMapper;
    }

    @PostMapping("/add")
    public AddPlayerResponse addPlayer(@RequestParam String playerName) {
        UUID playerId = gameService.joinToGame(playerName);
        return new AddPlayerResponse(playerId.toString());
    }

    @GetMapping("/{id}/cards")
    public Collection<CardDto> getCards(@PathVariable String id) {
        return cardMapper.mapToDtos(roundService.getPlayerCards(id));
    }

    @PostMapping("/{id}/activation-status")
    public void changePlayerActiveStatus(@PathVariable String id, @RequestParam boolean isActive) {
        gameService.changeActiveStatus(UUID.fromString(id), isActive);
    }

    @PostMapping("/{id}/all-in")
    public void allIn(@PathVariable String id) {
        roundService.allIn();
    }

    @PostMapping("/{id}/bid")
    public void bid(@PathVariable String id, @RequestParam int amount) {
        roundService.bid(amount);
    }

    @PostMapping("/{id}/fold")
    public void fold(@PathVariable String id) {
        roundService.fold();
    }

    @PostMapping("/{id}/buy-in")
    public void buyIn(@PathVariable String id) {
        gameService.buyIn(UUID.fromString(id));
    }

    @DeleteMapping("{id}/remove")
    public void removePlayer(@PathVariable String id) {
        gameService.removePlayer(UUID.fromString(id));
    }
}
