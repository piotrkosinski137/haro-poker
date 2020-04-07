package app.domain.player


import spock.lang.Specification

class PlayerServiceTest extends Specification{

    def 'should update players balance'() {
        given:
        Deque<Player> gamePlayers = new ArrayDeque<>(List.of(new Player(1,"Toms"), new Player(2,"Kosa")));
        //Deque<Player> roundPlayers = new ArrayDeque<>(List.of(new RoundPlayer(1,500), new RoundPlayer(2,500)));

        when:
        gamePlayers

        then:
        false
    }
}
