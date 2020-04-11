package app.web.websocket;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import app.web.websocket.dto.*;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class GameSocketController {

    private final GameService gameService;
    private final RoundService roundService;
    private final GamePlayerMapper gamePlayerMapper;
    private final RoundPlayerMapper roundPlayerMapper;
    private final CardMapper cardMapper;

    public GameSocketController(GameService gameService,
                                RoundService roundService,
                                GamePlayerMapper gamePlayerMapper,
                                RoundPlayerMapper roundPlayerMapper,
                                CardMapper cardMapper) {
        this.gameService = gameService;
        this.roundService = roundService;
        this.gamePlayerMapper = gamePlayerMapper;
        this.roundPlayerMapper = roundPlayerMapper;
        this.cardMapper = cardMapper;
    }

    @SubscribeMapping("/topic/game-players")
    public Collection<GamePlayerDto> subscribeToGamePlayers() {
        return gamePlayerMapper.mapToDtos(gameService.getPlayers());
    }

    @SubscribeMapping("/topic/round-players")
    public Collection<RoundPlayerDto> subscribeToRoundPlayers() {
        return roundPlayerMapper.mapToDtos(roundService.getPlayers());
    }

    @SubscribeMapping("/topic/cards")
    public Collection<CardDto> subscribeToTableCards() {
        return cardMapper.mapToDtos(roundService.getTableCards());
    }

    @SubscribeMapping("/topic/game")
    public GameDto subscribeToGame() {
        return new GameDto(gameService.getBlinds(), gameService.getTimeStamp());
    }

    //    Blueprint how to get messages directly via websocket
//    @MessageMapping("/players")
//    public void addPlayer(@Payload String playerName) {
//        gameService.joinToGame(playerName);
//        template.convertAndSend("/topic/players", playerMapper.mapToDtos(gameService.getPlayers()));
//    }
}
