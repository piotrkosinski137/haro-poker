package app.web.websocket;

import app.domain.event.GameChanged;
import app.domain.event.GamePlayersChanged;
import app.domain.event.RoundChanged;
import app.domain.event.RoundPlayersChanged;
import app.web.websocket.dto.GameDto;
import app.web.websocket.dto.GamePlayerMapper;
import app.web.websocket.dto.RoundMapper;
import app.web.websocket.dto.RoundPlayerMapper;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {

    private final SimpMessagingTemplate template;
    private final RoundPlayerMapper roundPlayerMapper;
    private final GamePlayerMapper gamePlayerMapper;
    private final RoundMapper roundMapper;

    public GameEventListener(SimpMessagingTemplate template, RoundPlayerMapper roundPlayerMapper, GamePlayerMapper gamePlayerMapper, RoundMapper roundMapper) {
        this.template = template;
        this.roundPlayerMapper = roundPlayerMapper;
        this.gamePlayerMapper = gamePlayerMapper;
        this.roundMapper = roundMapper;
    }

    @EventListener
    public void handleRoundPlayersChange(RoundPlayersChanged event) {
        template.convertAndSend("/topic/round-players", roundPlayerMapper.mapToDtos(event.getRoundPlayers()));
    }

    @EventListener
    public void handleGamePlayersChange(GamePlayersChanged event) {
        template.convertAndSend("/topic/game-players", gamePlayerMapper.mapToDtos(event.getGamePlayers()));
    }

    @EventListener
    public void handleGameChange(GameChanged event) {
        template.convertAndSend("/topic/game", new GameDto(event.getBlinds(), event.getGameTimeStamp()));
    }

    @EventListener
    public void handleRoundChanged(RoundChanged event) {
        template.convertAndSend("/topic/cards", roundMapper.mapToDto(event.getRound()));
    }
}
