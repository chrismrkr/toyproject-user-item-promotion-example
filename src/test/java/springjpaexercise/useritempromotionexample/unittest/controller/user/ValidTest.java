package springjpaexercise.useritempromotionexample.unittest.controller.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springjpaexercise.useritempromotionexample.controller.UserController;
import springjpaexercise.useritempromotionexample.repository.UserRepository;
import springjpaexercise.useritempromotionexample.service.impl.UserServiceImpl;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ValidTest {
    @Autowired UserController userController;
    @Autowired MockMvc mockMvc;
    @Test
    @DisplayName("회원 저장 실패 : blank username")
    void invalidUsername() throws Exception {
        // given
        String requestBody = "{\"username\": \"123\", \"userType\": \"GENERAL\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType("application/json")
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("회원명을 입력해주세요"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    @DisplayName("회원 저장 실패 : empty username")
    void emptyUsername() {
        // given

    }
    @Test
    @DisplayName("회원 저장 실패 : blank userType")
    void emptyUserType() {
        // given
    }
    @Test
    @DisplayName("회원 저장 실패 : empty userType")
    void blankUserType() {

    }
    @Test
    @DisplayName("회원 저장 실패 : invalid userType")
    void invalidUserType() {

    }
    @Test
    @DisplayName("회원 저장 실패 : invalid userStat")
    void invalidUserStat() {

    }
}
