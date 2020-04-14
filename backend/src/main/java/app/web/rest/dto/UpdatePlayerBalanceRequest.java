package app.web.rest.dto;

import java.util.List;

public class UpdatePlayerBalanceRequest {
    private List<PlayerMoney> playerMoney;

    public List<PlayerMoney> getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(List<PlayerMoney> playerMoney) {
        this.playerMoney = playerMoney;
    }
}
