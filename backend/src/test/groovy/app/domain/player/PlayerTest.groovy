package app.domain.player

import spock.lang.Specification

class PlayerTest extends Specification {

    //def player = new GamePlayer(1, "Toms")

    def 'should create new ready for game player'() {
        when:
        player

        then:
        player.getBalance() == 1000
        player.getName() == "Toms"
        player.getId() == 1
        player.isActive()
    }

    def 'should deactivate player'() {
        when: 'when balance is lower or equals 0'
        player.updateBalance(0)

        then:
        !player.isActive()
    }

    def 'should give player a break'() {
        when:
        player.deactivatePlayer()

        then:
        !player.isActive()
    }

    def 'should activate player'() {
        when:
        player.updateBalance(0)
        player.updateBalance(500)

        then:
        player.isActive()
        player.getBalance() == 500
    }
}
