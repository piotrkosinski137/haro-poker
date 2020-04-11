package app.domain.game

import spock.lang.Specification

class GameTest extends Specification {

    def game = new Game()

    def "should add player with correctly assigned table number"() {
        given:
        def playerName1 = "player1"
        def playerName2 = "player2"

        when: "first player is added"
        game.addPlayer(playerName1)

        then:
        with(game.getGamePlayers()) {
            size() == 1
            stream().filter({ player -> player.getTableNumber() == 1 })
                    .findFirst()
                    .isPresent()
        }

        when: "second player is added"
        game.addPlayer(playerName2)

        then:
        with(game.getGamePlayers()) {
            size() == 2
            stream().filter({ player -> player.getTableNumber() == 2 })
                    .findFirst()
                    .isPresent()
        }
    }

    def "should fail when game is full"() {
        when:
        game.addPlayer("player1")
        game.addPlayer("player2")
        game.addPlayer("player3")
        game.addPlayer("player4")
        game.addPlayer("player5")
        game.addPlayer("player6")
        game.addPlayer("player7")
        game.addPlayer("player8")

        then:
        thrown GameIsFull
    }
}
