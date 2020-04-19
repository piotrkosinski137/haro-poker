package app.domain.game;

import app.domain.player.GamePlayer;
import app.web.rest.dto.UpdatePlayerBalanceRequest;
import java.util.Collection;
import java.util.UUID;

public interface GameService {
    
    void startGame();

    void startRound();

    void setEntryFee(final int entryFee);

    void finishRound(final UUID winnerPlayerId);

    void updateBlinds(int small);
    
    void manualFinishRound(UpdatePlayerBalanceRequest updateBalances);

    void manualNextRound();
    
    GameDto getGameDto();

    /////
    UUID joinToGame(String playerName);
    
    void buyIn(UUID playerId);

    void changeActiveStatus(UUID id, boolean isActive);

    void removePlayer(UUID id);

    Collection<GamePlayer> getPlayers();
}
    
    
