package app.web.websocket;

import app.domain.game.GameService;
import app.domain.round.RoundService;
import app.web.websocket.dto.CardDto;
import app.web.websocket.dto.CardMapper;
import app.web.websocket.dto.PlayerDto;
import app.web.websocket.dto.PlayerMapper;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Collection;

@Controller
public class GameSocketController {

    private final GameService gameService;
    private final RoundService roundService;
    private final PlayerMapper playerMapper;
    private final CardMapper cardMapper;

    public GameSocketController(GameService gameService, RoundService roundService, PlayerMapper playerMapper, CardMapper cardMapper) {
        this.gameService = gameService;
        this.roundService = roundService;
        this.playerMapper = playerMapper;
        this.cardMapper = cardMapper;
    }

    @SubscribeMapping("/topic/players")
    public Collection<PlayerDto> subscribeToPlayerList() {
        // TODO if game haven't started return gamePlayers, else return roundPlayers
        return playerMapper.mapToDtos(gameService.getPlayers());
    }

    @SubscribeMapping("/topic/cards")
    public Collection<CardDto> subscribeToTableCards() {
        //TODO start game functionality - create round and tableCards to the round
//        return cardMapper.mapToDtos(roundService.getTableCards());
        return Arrays.asList(new CardDto("7", "S"), new CardDto("9", "H"), new CardDto("10", "H"));
    }

    //    Blueprint how to get messages directly via websocket
//    @MessageMapping("/players")
//    public void addPlayer(@Payload String playerName) {
//        gameService.joinToGame(playerName);
//        template.convertAndSend("/topic/players", playerMapper.mapToDtos(gameService.getPlayers()));
//    }
}
