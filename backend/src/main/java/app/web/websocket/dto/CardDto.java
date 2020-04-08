package app.web.websocket.dto;

public class CardDto {
    private String rank;
    private String suit;

    private CardDto() {
    }

    //TODO remove when tableCard mockup will not be needed
    public CardDto(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }
}
