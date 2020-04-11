package app.domain

import spock.lang.Specification

class TimeTest extends Specification {

    def "should create time from seconds"() {
        given:
        def time = new Time()

        when:
        time.update(totalSeconds)

        then:
        with(time) {
            getSeconds() == seconds
            getMinutes() == minutes
            getHours() == hours
        }

        where:
        totalSeconds | seconds | minutes | hours
        60           | 0       | 1       | 0
        61           | 1       | 1       | 0
        5432         | 32      | 30      | 1
    }
}
