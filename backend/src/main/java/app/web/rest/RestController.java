package app.web.rest;

public class RestController {

    public void bid(int playerId, int amount) {
//        update player amounts
//        if(all have the same bids) {
//            template.convertAndSend("/topic/cards", cards);
//        }
//        change current player

        //template.convertAndSend("/topic/players", players);
    }

    public void startGame() {
        //template.convertAndSend("/topic/cards", cards);

        //foreach player
            //template.convertAndSend("/topic/player/n", twoCards);
    }

    public void anyOtherEndpoint(int playerId, Object something) {
        // update players stored inMemory
        // template.convertAndSend("/topic/players", players)
    }


//    startGame -> pick active player, give cards for each player
//    bet/raise/check -> update balance turnBid, roundBid, if stage is over give new cards and clear turnBids
//    fold -> hasFolded true
//    finishRound -> give all roundBids to one player, clear table cards, handcards, roundBids, turnBids
//    goAfk -> active false
//    returnFromAfk active true

// data about round stage should stay on backend, it's irrelevant for frontend
}
