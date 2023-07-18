package springjpaexercise.useritempromotionexample.unittest.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SaveTest {
    @Autowired MockMvc mockMvc;

    @Test
    @DisplayName("회원 저장 성공")
    void successUserCreate() throws Exception {
        // given
        String requestBody = "{\"username\": \"aaaaaa1a\", \"userType\": \"GENERAL\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("회원 저장 실패 : invalid username pattern")
    void invalidUsernamePattern() throws Exception {
        // given
        String requestBody = "{\"username\": \"1a1\", \"userType\": \"GENERAL\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("회원 저장 실패 : empty username")
    void invalidUsername() throws Exception {
        // given
        String requestBody = "{\"username\": \"\", \"userType\": \"GENERAL\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("회원 저장 실패 : blank username")
    void emptyUsername() throws Exception {
        // given
        String requestBody = "{\"username\": \"  \", \"userType\": \"GENERAL\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("회원 저장 실패 : null username")
    void nullUsername() throws Exception {
        // given
        String requestBody = "{\"userType\": \"GENERAL\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }

    @Test
    @DisplayName("회원 저장 실패 : blank userType")
    void blankUserType() throws Exception {
        // given
        String requestBody = "{\"username\": \"aaa\", \"userType\": \"\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("회원 저장 실패 : empty userType")
    void emptyUserType() throws Exception {
        // given
        String requestBody = "{\"username\": \"aaa\", \"userType\": \"   \"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }

    @Test
    @DisplayName("회원 저장 실패 : null userType")
    void nullUserType() throws Exception {
        // given
        String requestBody = "{\"username\": \"aaa\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("회원 저장 실패 : invalid username Pattern & null userType")
    void invalidUsernamePatternAndNullUserType() throws Exception {
        // given
        String requestBody = "{\"username\": \"11aa\"}";
        // when
        MvcResult result = mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
}
