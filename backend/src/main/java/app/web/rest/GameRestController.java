package app.web.rest;

import app.domain.game.GameService;
import app.domain.round.RoundPlayerServiceImpl;
import app.domain.round.RoundService;
import app.web.rest.dto.UpdatePlayerBalanceRequest;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameRestController {

    private final GameService gameService;
    private final RoundService roundService;
    private final RoundPlayerServiceImpl roundPlayerService;

    public GameRestController(GameService gameService, RoundService roundService, RoundPlayerServiceImpl roundPlayerService) {
        this.gameService = gameService;
        this.roundService = roundService;
        this.roundPlayerService = roundPlayerService;
    }

    @PostMapping("/start")
    public void startGame() {
        gameService.startGame();
    }

    @PostMapping("/round/new")
    public void newRound() {
        gameService.startRound();
    }

    @PostMapping("/entry-fee")
    public void setEntryFee(@RequestParam int entryFee) {
        gameService.setEntryFee(entryFee);
    }

    @PostMapping("/round/finish/{id}")
    public void finishRound(@PathVariable String id) {
        roundPlayerService.finishRoundWithWinner(UUID.fromString(id));
    }

    @PutMapping("/blinds/update")
    public void updateBlinds(@RequestParam int small) {
        gameService.updateBlinds(small);
    }

    @PutMapping("/admin/round-bids/update")
    public void manualFinishRound(@RequestBody UpdatePlayerBalanceRequest updateBids) {
        roundPlayerService.manualFinishRound(updateBids);
    }

    @PostMapping("/round/next")
    public void manualNextRound() {
        roundService.startRound();
    }
}
