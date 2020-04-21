package app.web.websocket;

import app.domain.game.GameDto;
import app.domain.game.GamePlayerDto;
import app.domain.game.GamePlayerService;
import app.domain.game.GameService;
import app.domain.round.RoundServiceImpl;
import app.domain.round.RoundDto;
import app.domain.round.RoundPlayerDto;
import app.domain.round.RoundPlayerMapper;
import java.util.Collection;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameSocketController {

    private final GameService gameService;
    private final GamePlayerService gamePlayerService;
    private final RoundServiceImpl roundService;
    private final RoundPlayerMapper roundPlayerMapper;

    public GameSocketController(GameService gameService, GamePlayerService gamePlayerService, RoundServiceImpl roundService, RoundPlayerMapper roundPlayerMapper) {
        this.gameService = gameService;
        this.gamePlayerService = gamePlayerService;
        this.roundService = roundService;
        this.roundPlayerMapper = roundPlayerMapper;
    }

    @SubscribeMapping("/topic/game-players")
    public Collection<GamePlayerDto> subscribeToGamePlayers() {
        return gamePlayerService.getPlayers();
    }

    @SubscribeMapping("/topic/round-players")
    public Collection<RoundPlayerDto> subscribeToRoundPlayers() {
        return roundPlayerMapper.mapToDtos(roundService.getPlayers());
    } //todo

    @SubscribeMapping("/topic/round")
    public RoundDto subscribeToRound() {
        return roundService.getRound();
    }

    @SubscribeMapping("/topic/game")
    public GameDto subscribeToGame() {
        return gameService.getGame();
    }
}
