package app.web;

public class RestController {

    public void bet(int playerId, int amount) {
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
}
