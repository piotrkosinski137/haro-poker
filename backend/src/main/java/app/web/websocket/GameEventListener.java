package app.web.websocket;

import app.domain.event.GameChanged;
import app.domain.event.GamePlayersChanged;
import app.domain.event.RoundPlayersChanged;
import app.domain.event.TableCardsChanged;
import app.web.websocket.dto.CardMapper;
import app.web.websocket.dto.GameDto;
import app.web.websocket.dto.GamePlayerMapper;
import app.web.websocket.dto.RoundPlayerMapper;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {

    private final SimpMessagingTemplate template;
    private final RoundPlayerMapper roundPlayerMapper;
    private final GamePlayerMapper gamePlayerMapper;
    private final CardMapper cardMapper;

    public GameEventListener(SimpMessagingTemplate template,
                             RoundPlayerMapper roundPlayerMapper,
                             GamePlayerMapper gamePlayerMapper,
                             CardMapper cardMapper) {
        this.template = template;
        this.roundPlayerMapper = roundPlayerMapper;
        this.gamePlayerMapper = gamePlayerMapper;
        this.cardMapper = cardMapper;
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
        template.convertAndSend("/topic/game", new GameDto(event.getBlinds()));
    }

    @EventListener
    public void handleTableCardsChange(TableCardsChanged event) {
        template.convertAndSend("/topic/cards", cardMapper.mapToDtos(event.getCards()));
    }
}
