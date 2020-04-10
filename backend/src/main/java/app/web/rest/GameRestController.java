package app.web.rest;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import app.web.websocket.dto.GameDto;
import app.web.websocket.dto.GamePlayerMapper;
import app.web.websocket.dto.RoundPlayerMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class GameRestController {

    private final SimpMessagingTemplate template;
    private final GameService gameService;
    private final GamePlayerMapper gamePlayerMapper;
    private final RoundPlayerMapper roundPlayerMapper;
    private final RoundService roundService;

    public GameRestController(SimpMessagingTemplate template,
                              GameService gameService,
                              GamePlayerMapper gamePlayerMapper,
                              RoundPlayerMapper roundPlayerMapper,
                              RoundService roundService) {
        this.template = template;
        this.gameService = gameService;
        this.gamePlayerMapper = gamePlayerMapper;
        this.roundPlayerMapper = roundPlayerMapper;
        this.roundService = roundService;
    }

    @PostMapping("/players/add")
    public AddPlayerResponse addPlayer(@RequestParam String playerName) {
        UUID playerId = gameService.joinToGame(playerName);
        template.convertAndSend("/topic/game-players", gamePlayerMapper.mapToDtos(gameService.getPlayers()));
        return new AddPlayerResponse(playerId.toString());
    }

    @PostMapping("/game/start")
    public void startRound() {
        gameService.startGame();
        template.convertAndSend("/topic/game-players", gamePlayerMapper.mapToDtos(gameService.getPlayers()));
        template.convertAndSend("/topic/round-players", roundPlayerMapper.mapToDtos(roundService.getPlayers()));
    }

    @PutMapping("/game/blinds/update")
    public void updateBlinds(@RequestParam int small) {
        gameService.updateBlinds(small);
        template.convertAndSend("/topic/game", new GameDto(gameService.getBlinds()));
    }

    //TODO active status will change immediately but it should change after given round
    @PostMapping("/players/{id}/activation-status")
    public void changePlayerActiveStatus(@PathVariable String id, @RequestParam boolean isActive) {
        gameService.changeActiveStatus(UUID.fromString(id), isActive);
        template.convertAndSend("/topic/game-players", gamePlayerMapper.mapToDtos(gameService.getPlayers()));
    }

    //TODO consider if id needed for security reasons
    @PostMapping("/players/{id}/bid")
    public void bid(@PathVariable String id, @RequestParam int amount) {
        roundService.bid(amount);
        template.convertAndSend("/topic/round-players", roundPlayerMapper.mapToDtos(roundService.getPlayers()));
    }

    //TODO consider if id needed for security reasons
    @PostMapping("/players/{id}/fold")
    public void fold(@PathVariable String id) {
        roundService.fold();
        template.convertAndSend("/topic/round-players", roundPlayerMapper.mapToDtos(roundService.getPlayers()));
    }
}
