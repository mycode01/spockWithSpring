package com.example.spockdemo.repo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.*

@DataJpaTest
class UserRepositoryTest extends Specification {
    @Autowired
    UserRepository userRepo;


    def "can not find"() {
        expect:
        userRepo.findUserByMarketUserId(marketUserId) == null
        where:
        marketUserId << ["testid1", "testid2"]
    }

    def "save user"(){
        expect:
        userRepo.save(new User(marketUserId, marketUserId + "@void.com")) != null

        where:
        marketUserId << ["testid1", "testid2"]
    }

    def "save and find"() {
        given:
        userRepo.save(new User(marketUserId, marketUserId + "@void.com"))

        when:
        def user = userRepo.findUserByMarketUserId(marketUserId)
        then:
        user.getEmail().contains(marketUserId)

        where:
        marketUserId << ["testid1", "something", "whatmail?"]
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme