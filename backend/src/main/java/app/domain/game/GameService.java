package app.domain.game;

import app.web.rest.dto.UpdatePlayerBalanceRequest;
import java.util.UUID;

public interface GameService {
    
    void startGame();

    void startRound();

    void setEntryFee(final int entryFee);

    void finishRound(final UUID winnerPlayerId);

    void updateBlinds(int small);
    
    void manualFinishRound(UpdatePlayerBalanceRequest updateBalances);

    void manualNextRound();
    
    GameDto getGame();
}
    
    
