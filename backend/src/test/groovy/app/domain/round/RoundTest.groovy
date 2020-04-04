package app.domain.round

import spock.lang.Specification

class RoundTest extends Specification {

    def "should check if given players bids are the same"() {
        given:
        def round = new Round()

        and: "round contains players"
        round.addRoundPlayers(roundPlayers)

        when:
        def result = round.playersBidsAreEqual()

        then:
        result == areBidsEqual

        where:
        roundPlayers                                                               | areBidsEqual
        [roundPlayer(300, false), roundPlayer(300, false)]                         | true
        [roundPlayer(300, false), roundPlayer(600, false)]                         | false
        [roundPlayer(200, true), roundPlayer(600, false), roundPlayer(600, false)] | true
    }

    private static def roundPlayer(int turnBid, hasFolded) {
        return [turnBid: turnBid, hasFolded: hasFolded] as RoundPlayer
    }
}
