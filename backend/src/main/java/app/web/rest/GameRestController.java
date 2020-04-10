package app.web.rest;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import app.web.websocket.dto.GamePlayerMapper;
import app.web.websocket.dto.RoundPlayerMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/round/start")
    public void startRound() {
        gameService.startRound();
        template.convertAndSend("/topic/round-players", roundPlayerMapper.mapToDtos(roundService.getPlayers()));
    }

    //TODO active status will change immediately but it should change after given round
    @PostMapping("/players/{id}/activation-status")
    public void changePlayerActiveStatus(@PathVariable String id) {
        gameService.changeActiveStatus(UUID.fromString(id));
        template.convertAndSend("/topic/game-players", gamePlayerMapper.mapToDtos(gameService.getPlayers()));
    }
}
