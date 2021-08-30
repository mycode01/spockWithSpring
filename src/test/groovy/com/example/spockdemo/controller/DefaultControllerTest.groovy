package com.example.spockdemo.controller

import com.example.spockdemo.common.exceptions.ErrorCode
import com.example.spockdemo.controller.model.FailureRes
import com.example.spockdemo.controller.model.ReqSendMail
import com.example.spockdemo.repo.UserRepository
import com.example.spockdemo.services.UserMailService
import com.example.spockdemo.services.exceptions.InvalidMailBodyException
import com.example.spockdemo.services.exceptions.InvalidMarketUserIdException
import com.example.spockdemo.services.exceptions.NotFoundUserException
import com.example.spockdemo.services.exceptions.SendMailException
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.result.ContentResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.*

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@AutoConfigureMockMvc
@WebMvcTest(controllers = [DefaultController])
class DefaultControllerTest extends Specification {
    @Autowired
    private MockMvc mvc
    @SpringBean
    UserMailService userMailService = Mock()
    @Autowired
    DefaultController defaultController

    @Autowired
    private ObjectMapper objectMapper;

    def "post return success"() {
        given:
        def content = objectMapper.writeValueAsString(
                new ReqSendMail(marketUserId: marketUserId, mailBody: mailBody))
        when:
        def res = mvc.perform(post("/mail")
                .content(content).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // deprecate 되었지만, utf8을 사용하지 않으면 한글이 깨짐

        then:
        res.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(content))
        1 * userMailService.sendMailByMarketUserId(_, _) >> {}


        where:
        marketUserId   | mailBody
        "marketUserId" | "쓰잘데 없는 내용"
        "userid1"      | "마찬가지로 쓰잘데 없는 내용"
    }

    def "service mailbody Assertion exception"() {
        given:
        def content = objectMapper.writeValueAsString(
                new ReqSendMail(marketUserId: marketUserId, mailBody: mailBody))
        def expectResult = objectMapper.writeValueAsString(new FailureRes(new InvalidMailBodyException()))
        when:
        def res = mvc.perform(post("/mail")
                .content(content).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // deprecate 되었지만, utf8을 사용하지 않으면 한글이 깨짐

        then:
        res.andExpect(status().is4xxClientError()).andExpect(MockMvcResultMatchers.content().string(expectResult))
        1 * userMailService.sendMailByMarketUserId(_, _) >> { throw new InvalidMailBodyException() }


        where:
        marketUserId   | mailBody
        "marketUserId" | "쓰잘데 없는 내용"
        "userid1"      | "마찬가지로 쓰잘데 없는 내용"
    }

    def "service marketUserId Assertion exception"() {
        given:
        def content = objectMapper.writeValueAsString(
                new ReqSendMail(marketUserId: marketUserId, mailBody: mailBody))
        def expectResult = objectMapper.writeValueAsString(new FailureRes(new InvalidMarketUserIdException()))
        when:
        def res = mvc.perform(post("/mail")
                .content(content).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // deprecate 되었지만, utf8을 사용하지 않으면 한글이 깨짐

        then:
        res.andExpect(status().is4xxClientError()).andExpect(MockMvcResultMatchers.content().string(expectResult))
        1 * userMailService.sendMailByMarketUserId(_, _) >> { throw new InvalidMarketUserIdException() }


        where:
        marketUserId   | mailBody
        "marketUserId" | "쓰잘데 없는 내용"
        "userid1"      | "마찬가지로 쓰잘데 없는 내용"
    }

    def "service NotFoundUser exception"() {
        given:
        def content = objectMapper.writeValueAsString(
                new ReqSendMail(marketUserId: marketUserId, mailBody: mailBody))
        def expectResult = objectMapper.writeValueAsString(new FailureRes(new NotFoundUserException()))
        when:
        def res = mvc.perform(post("/mail")
                .content(content).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // deprecate 되었지만, utf8을 사용하지 않으면 한글이 깨짐

        then:
        res.andExpect(status().is4xxClientError()).andExpect(MockMvcResultMatchers.content().string(expectResult))
        1 * userMailService.sendMailByMarketUserId(_, _) >> { throw new NotFoundUserException() }


        where:
        marketUserId   | mailBody
        "marketUserId" | "쓰잘데 없는 내용"
        "userid1"      | "마찬가지로 쓰잘데 없는 내용"
    }


    def "service SendMailException exception"() {
        given:
        def content = objectMapper.writeValueAsString(
                new ReqSendMail(marketUserId: marketUserId, mailBody: mailBody))
        def expectResult = objectMapper.writeValueAsString(new FailureRes(new SendMailException()))
        when:
        def res = mvc.perform(post("/mail")
                .content(content).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // deprecate 되었지만, utf8을 사용하지 않으면 한글이 깨짐

        then:
        res.andExpect(status().is5xxServerError()).andExpect(MockMvcResultMatchers.content().string(expectResult))
        1 * userMailService.sendMailByMarketUserId(_, _) >> { throw new SendMailException() }


        where:
        marketUserId   | mailBody
        "marketUserId" | "쓰잘데 없는 내용"
        "userid1"      | "마찬가지로 쓰잘데 없는 내용"
    }


    def "dto validation exception"() {
        given:
        def content = objectMapper.writeValueAsString(
                new ReqSendMail(marketUserId: null, mailBody: null))
        when:
        def res = mvc.perform(post("/mail")
                .content(content).accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)) // deprecate 되었지만, utf8을 사용하지 않으면 한글이 깨짐

        then:
        res.andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content()
                        .string(containsString(ErrorCode.WRONG_PARAMETER.getCode())))
        0 * userMailService.sendMailByMarketUserId(_, _)

    }

//    def "test exception Handler"() {
//        when:
//        ResponseEntity result =
//        default.exceptionHandler(null)
//
//        then:
//        result == null
//    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme