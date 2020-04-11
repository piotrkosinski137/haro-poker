package app.web.websocket.dto;

public class CardDto {
    private String rank;
    private String suit;

    private CardDto() {
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
