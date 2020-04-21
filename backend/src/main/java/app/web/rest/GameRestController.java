package app.web.rest;

import app.domain.game.GameService;

import app.domain.round.RoundServiceImpl;
import java.util.UUID;

import app.web.rest.dto.UpdatePlayerBalanceRequest;
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
    private final RoundServiceImpl roundService;

    public GameRestController(GameService gameService, RoundServiceImpl roundService) {
        this.gameService = gameService;
        this.roundService = roundService;
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
        roundService.finishRoundWithWinner(UUID.fromString(id));
    }

    @PutMapping("/blinds/update")
    public void updateBlinds(@RequestParam int small) {
        gameService.updateBlinds(small);
    }

    @PutMapping("/admin/round-bids/update")
    public void manualFinishRound(@RequestBody UpdatePlayerBalanceRequest updateBids) {
        roundService.manualFinishRound(updateBids);
    }

    @PostMapping("/round/next")
    public void manualNextRound() {
        roundService.manualNextRound();
    }
}
