package app.domain.round;

import app.domain.card.CardDto;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoundPlayerDto {
    private String id;
    private Integer tableNumber;
    private Set<CardDto> cardsInHand;
    private int balance;
    private int turnBid;
    private int roundBid;
    private boolean hasTurn;
    private boolean hasFolded;
    private String playerPosition;

    private RoundPlayerDto() {
    }

    public static RoundPlayerDto fromRoundPlayer(final RoundPlayer roundPlayer) {
        final RoundPlayerDto roundPlayerDto = new RoundPlayerDto();
        roundPlayerDto.setId(roundPlayer.getId().toString());
        roundPlayerDto.setTableNumber(roundPlayer.getTableNumber());
        roundPlayerDto.setCardsInHand(new HashSet<>());
        roundPlayerDto.setBalance(roundPlayer.getBalance());
        roundPlayerDto.setTurnBid(roundPlayer.getTurnBid());
        roundPlayerDto.setRoundBid(roundPlayer.getRoundBid());
        roundPlayerDto.setHasTurn(roundPlayer.isHasTurn());
        roundPlayerDto.setHasFolded(roundPlayer.isHasFolded());
        roundPlayerDto.setPlayerPosition(roundPlayer.getPlayerPosition().toString());
        return roundPlayerDto;
    }

    public static RoundPlayerDto fromRoundPlayerWithCards(final RoundPlayer roundPlayer) {
        final RoundPlayerDto roundPlayerDto = fromRoundPlayer(roundPlayer);
        roundPlayerDto.setCardsInHand(roundPlayer.getCardsInHand());
        return roundPlayerDto;
    }

    public static Deque<RoundPlayerDto> fromRoundPlayersCollection(final Deque<RoundPlayer> gamePlayers) {
        return gamePlayers.stream().map(RoundPlayerDto::fromRoundPlayer)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public static Deque<RoundPlayerDto> fromRoundPlayersCollectionWithCards(final Deque<RoundPlayer> gamePlayers) {
        return gamePlayers.stream().map(RoundPlayerDto::fromRoundPlayerWithCards)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Set<CardDto> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(Set<CardDto> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTurnBid() {
        return turnBid;
    }

    public void setTurnBid(int turnBid) {
        this.turnBid = turnBid;
    }

    public int getRoundBid() {
        return roundBid;
    }

    public void setRoundBid(int roundBid) {
        this.roundBid = roundBid;
    }

    public boolean isHasTurn() {
        return hasTurn;
    }

    public void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    public boolean isHasFolded() {
        return hasFolded;
    }

    public void setHasFolded(boolean hasFolded) {
        this.hasFolded = hasFolded;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }
}
