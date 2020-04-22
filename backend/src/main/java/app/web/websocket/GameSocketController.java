package app.web.websocket;

import app.domain.game.GameDto;
import app.domain.game.GamePlayerDto;
import app.domain.game.GamePlayerService;
import app.domain.game.GameService;
import app.domain.round.RoundDto;
import app.domain.round.RoundPlayerDto;
import app.domain.round.RoundPlayerServiceImpl;
import app.domain.round.RoundService;
import java.util.Collection;
import java.util.Deque;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameSocketController {

    private final GameService gameService;
    private final GamePlayerService gamePlayerService;
    private final RoundService roundService;
    private final RoundPlayerServiceImpl roundPlayerService;

    public GameSocketController(GameService gameService, GamePlayerService gamePlayerService, RoundService roundService,
            RoundPlayerServiceImpl roundPlayerService) {
        this.gameService = gameService;
        this.gamePlayerService = gamePlayerService;
        this.roundService = roundService;
        this.roundPlayerService = roundPlayerService;
    }

    @SubscribeMapping("/topic/game-players")
    public Collection<GamePlayerDto> subscribeToGamePlayers() {
        return gamePlayerService.getPlayers();
    }

    @SubscribeMapping("/topic/round-players")
    public Deque<RoundPlayerDto> subscribeToRoundPlayers() {
        return roundPlayerService.getRoundPlayers();
    }

    @SubscribeMapping("/topic/round")
    public RoundDto subscribeToRound() {
        return roundService.getRound();
    }

    @SubscribeMapping("/topic/game")
    public GameDto subscribeToGame() {
        return gameService.getGame();
    }
}
