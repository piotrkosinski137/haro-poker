package app.web.rest;

import app.domain.game.GameService;
import app.web.websocket.dto.PlayerMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PlayerRestController {

    private final SimpMessagingTemplate template;
    private final GameService gameService;
    private final PlayerMapper playerMapper;

    public PlayerRestController(SimpMessagingTemplate template, GameService gameService, PlayerMapper playerMapper) {
        this.template = template;
        this.gameService = gameService;
        this.playerMapper = playerMapper;
    }

    @PostMapping("/players/add")
    public AddPlayerResponse addPlayer(@RequestParam String playerName) {
        UUID playerId = gameService.joinToGame(playerName);
        template.convertAndSend("/topic/players", playerMapper.mapToDtos(gameService.getPlayers()));
        return new AddPlayerResponse(playerId.toString());
    }
}
