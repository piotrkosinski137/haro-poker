package app.web.websocket;

import app.domain.game.GameChanged;
import app.domain.game.GamePlayersChanged;
import app.domain.round.RoundChanged;
import app.domain.round.RoundPlayersChanged;
import app.domain.round.RoundPlayerMapper;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameEventListener {

    private final SimpMessagingTemplate template;
    private final RoundPlayerMapper roundPlayerMapper;

    public GameEventListener(SimpMessagingTemplate template, RoundPlayerMapper roundPlayerMapper) {
        this.template = template;
        this.roundPlayerMapper = roundPlayerMapper;
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
    public void handleRoundPlayersChange(RoundPlayersChanged event) { //todo
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
