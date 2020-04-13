package app.web.websocket;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import app.web.websocket.dto.GameDto;
import app.web.websocket.dto.GamePlayerDto;
import app.web.websocket.dto.GamePlayerMapper;
import app.web.websocket.dto.RoundDto;
import app.web.websocket.dto.RoundMapper;
import app.web.websocket.dto.RoundPlayerDto;
import app.web.websocket.dto.RoundPlayerMapper;
import java.util.Collection;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameSocketController {

    private final GameService gameService;
    private final RoundService roundService;
    private final GamePlayerMapper gamePlayerMapper;
    private final RoundPlayerMapper roundPlayerMapper;
    private final RoundMapper roundMapper;

    public GameSocketController(GameService gameService, RoundService roundService, GamePlayerMapper gamePlayerMapper, RoundPlayerMapper roundPlayerMapper,
            RoundMapper roundMapper) {
        this.gameService = gameService;
        this.roundService = roundService;
        this.gamePlayerMapper = gamePlayerMapper;
        this.roundPlayerMapper = roundPlayerMapper;
        this.roundMapper = roundMapper;
    }

    @SubscribeMapping("/topic/game-players")
    public Collection<GamePlayerDto> subscribeToGamePlayers() {
        return gamePlayerMapper.mapToDtos(gameService.getPlayers());
    }

    @SubscribeMapping("/topic/round-players")
    public Collection<RoundPlayerDto> subscribeToRoundPlayers() {
        return roundPlayerMapper.mapToDtos(roundService.getPlayers());
    }

    @SubscribeMapping("/topic/round")
    public RoundDto subscribeToRound() {
        return roundMapper.mapToDto(roundService.getRound());
    }

    @SubscribeMapping("/topic/game")
    public GameDto subscribeToGame() {
        return new GameDto(gameService.getBlinds(), gameService.getTimeStamp());
    }
}
