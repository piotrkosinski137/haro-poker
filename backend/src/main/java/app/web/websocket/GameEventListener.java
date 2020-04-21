package app.web.websocket;

import app.domain.round.RoundChanged;
import app.domain.round.RoundPlayersChanged;
import app.domain.game.GameChanged;
import app.domain.game.GamePlayersChanged;
import app.web.websocket.dto.RoundMapper;
import app.web.websocket.dto.RoundPlayerMapper;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {

    private final SimpMessagingTemplate template;
    private final RoundPlayerMapper roundPlayerMapper;
    private final RoundMapper roundMapper;

    public GameEventListener(SimpMessagingTemplate template, RoundPlayerMapper roundPlayerMapper, RoundMapper roundMapper) {
        this.template = template;
        this.roundPlayerMapper = roundPlayerMapper;
        this.roundMapper = roundMapper;
    }

    @EventListener
    public void handleGamePlayersChange(GamePlayersChanged event) {
        template.convertAndSend("/topic/game-players", event.getGamePlayers());
    }

    @EventListener
    public void handleGameChange(GameChanged event) {
        template.convertAndSend("/topic/game", event.getGameDto());
    }

    @EventListener
    public void handleRoundPlayersChange(RoundPlayersChanged event) {
        if (event.isWithCards()) {
            template.convertAndSend("/topic/round-players", roundPlayerMapper.mapToDtosWithCards(event.getRoundPlayers()));
        } else {
            template.convertAndSend("/topic/round-players", roundPlayerMapper.mapToDtos(event.getRoundPlayers()));
        }
    }

    @EventListener
    public void handleRoundChanged(RoundChanged event) {
        template.convertAndSend("/topic/round", event.getRound());
    }
}
