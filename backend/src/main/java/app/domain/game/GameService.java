package app.domain.game;

public interface GameService {
    
    void startGame();

    void startRound();

    void setEntryFee(final int entryFee);

    void updateBlinds(int small);
    
    GameDto getGame();
}
    
    
