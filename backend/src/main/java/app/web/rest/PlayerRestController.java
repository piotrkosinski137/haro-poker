package app.web.rest;

import app.domain.card.CardDto;
import app.domain.game.GamePlayerService;
import app.domain.round.RoundPlayerServiceImpl;
import java.util.Collection;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerRestController {

    private final GamePlayerService gamePlayerService;
    private final RoundPlayerServiceImpl roundPlayerService;

    public PlayerRestController(GamePlayerService gamePlayerService, RoundPlayerServiceImpl roundPlayerService) {
        this.gamePlayerService = gamePlayerService;
        this.roundPlayerService = roundPlayerService;
    }

    @PostMapping("/add")
    public AddPlayerResponse addPlayer(@RequestParam String playerName) {
        UUID playerId = gamePlayerService.joinToGame(playerName);
        return new AddPlayerResponse(playerId.toString());
    }

    @GetMapping("/{id}/cards")
    public Collection<CardDto> getCards(@PathVariable String id) {
        return roundPlayerService.getPlayerCards(id);
    }

    @PostMapping("/{id}/all-in")
    public void allIn(@PathVariable String id) {
        roundPlayerService.allIn();
    }

    @PostMapping("/{id}/bid")
    public void bid(@PathVariable String id, @RequestParam int amount) {
        roundPlayerService.bid(amount);
    }

    @PostMapping("/{id}/fold")
    public void fold(@PathVariable String id) {
        roundPlayerService.fold();
    }

    @DeleteMapping("{id}/remove")
    public void removePlayer(@PathVariable String id) {
        roundPlayerService.removeRoundPlayer(UUID.fromString(id));
    }

    //todo in next stage put to other controller
    @PostMapping("/{id}/activation-status")
    public void changePlayerActiveStatus(@PathVariable String id, @RequestParam boolean isActive) {
        gamePlayerService.changeActiveStatus(UUID.fromString(id));
    }

    @PostMapping("/{id}/buy-in")
    public void buyIn(@PathVariable String id) {
        gamePlayerService.buyIn(UUID.fromString(id));
    }
}
