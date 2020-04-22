package app.web.websocket;

import app.domain.game.GameChanged;
import app.domain.game.GamePlayersChanged;
import app.domain.round.RoundChanged;
import app.domain.round.RoundPlayersChanged;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {

    private final SimpMessagingTemplate template;

    public GameEventListener(SimpMessagingTemplate template) {
        this.template = template;
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
            template.convertAndSend("/topic/round-players", event.getRoundPlayers());
    }

    @EventListener
    public void handleRoundChanged(RoundChanged event) {
        template.convertAndSend("/topic/round", event.getRound());
    }
}
