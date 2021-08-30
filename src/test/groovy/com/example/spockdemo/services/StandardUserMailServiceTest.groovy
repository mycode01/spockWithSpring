package com.example.spockdemo.services

import com.example.spockdemo.repo.User
import com.example.spockdemo.repo.UserRepository
import com.example.spockdemo.services.exceptions.InvalidMailBodyException
import com.example.spockdemo.services.exceptions.InvalidMarketUserIdException
import com.example.spockdemo.services.exceptions.NotFoundUserException
import com.example.spockdemo.services.exceptions.SendMailException
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class StandardUserMailServiceTest extends Specification {
    def "validate id 테스트"() {
        given:
        def mockUserRepo = Mock(UserRepository.class)
        def mockSender = Mock(EMailSender.class)
        UserMailService userMailService = new StandardUserMailService(mockUserRepo, mockSender)

        when:
        userMailService.sendMailByMarketUserId(marketUserId, body)

        then:
        thrown(InvalidMarketUserIdException.class)

        where:
        marketUserId    | body
        "test"          | "패스 "
        "1df23"         | "아무"
        "abcedfghijklm" | "말이"
    }

    def "validate body 테스트"() {
        given:
        def mockUserRepo = Mock(UserRepository.class)
        def mockSender = Mock(EMailSender.class)
        UserMailService userMailService = new StandardUserMailService(mockUserRepo, mockSender)

        when:
        userMailService.sendMailByMarketUserId(marketUserId, body)

        then:
        thrown(InvalidMailBodyException.class)
        0 * userMailService.userRepo.findUserByMarketUserId

        where:
        marketUserId | body
        "괜찮은아이디"     | "욕설이포함된 메일 "
        "i'm_ok"     | "비속어도 포함된 메일 내용 "
        "abcedf"     | "말이 너무 많은 메일" * 1000
    }

    def "User 가 없을 경우의 에러"() {
        given:
        def mockUserRepo = Mock(UserRepository.class)
        def mockSender = Mock(EMailSender.class)
        UserMailService userMailService = new StandardUserMailService(mockUserRepo, mockSender)

        when:
        userMailService.sendMailByMarketUserId(marketUserId, mailBody)

        then:
        thrown(NotFoundUserException.class)
        1 * mockUserRepo.findUserByMarketUserId(_)
        0 * mockSender._

        where:
        marketUserId | mailBody
        "아무영향이"      | "없는"
        "파라메터들"      | "스텁이 null만 줌"

    }

    def "메일 발송중 에러 "() {
        given:
        def mockUserRepo = Mock(UserRepository.class)
        def mockSender = Mock(EMailSender.class)
        UserMailService userMailService = new StandardUserMailService(mockUserRepo, mockSender)

        when:
        userMailService.sendMailByMarketUserId(marketUserId, mailBody)

        then:
        thrown(SendMailException.class)
        1 * mockUserRepo.findUserByMarketUserId(_) >> { args -> new User(args[0], "nowhere@void.com") }
        1 * mockSender.sendEMail(_) >> { throw new RuntimeException() }

        where:
        marketUserId   | mailBody
        "marketUserId" | "본문"
    }

    def "메일 발송 완료"() {
        given:
        def mockUserRepo = Mock(UserRepository.class)
        def mockSender = Mock(EMailSender.class)
        UserMailService userMailService = new StandardUserMailService(mockUserRepo, mockSender)

        when:
        userMailService.sendMailByMarketUserId(marketUserId, mailBody)

        then:
        1 * mockUserRepo.findUserByMarketUserId(_) >> { args -> new User(args[0], marketUserId + "@void.com") }
        1 * mockSender.sendEMail(_) >> { EMailSender.Mail mail ->
            assert mail.getReceiver().contains(marketUserId)
            assert mail.getBody() == mailBody
        }

        where:
        marketUserId   | mailBody
        "marketUserId" | "본문"
        "nobody"       | "test"
    }

}
