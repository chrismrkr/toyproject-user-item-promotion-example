package springjpaexercise.useritempromotionexample.unittest.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;
import springjpaexercise.useritempromotionexample.repository.UserRepository;
import springjpaexercise.useritempromotionexample.service.UserService;

import javax.persistence.EntityManager;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UpdateTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 수정 성공 : username 변경")
    void successUsernameUpdate() throws Exception {
        // given
        User user = User.builder().username("aaa")
                .userType(UserType.GENERAL).userStat(UserStat.ACCESSION).build();
        User save = userRepository.save(user);

        // when
        String requestBody = "{\"username\": \"aaachange\", \"userType\": \"GENERAL\"}";
        MvcResult result = mockMvc.perform(put("/user/{id}", save.getId())
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
    @DisplayName("회원 수정 성공 : userType 변경(GENERAL -> ENTERPRISE)")
    void successUserTypeUpdate() throws Exception {
        // given
        User user = User.builder().username("aaaaaaaa")
                .userType(UserType.GENERAL).userStat(UserStat.ACCESSION).build();
        User save = userRepository.save(user);

        // when
        String requestBody = "{\"username\": \"aaaaaaaa\", \"userType\": \"ENTERPRISE\"}";
        MvcResult result = mockMvc.perform(put("/user/{id}", save.getId())
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
    @DisplayName("회원 수정 성공 : userStat 변경(ACCESSION -> WITHDRAWAL)")
    void successUserStatUpdate() throws Exception {
        // given
        User user = User.builder().username("aaaaaaaa")
                .userType(UserType.GENERAL).userStat(UserStat.ACCESSION).build();
        User save = userRepository.save(user);

        // when
        String requestBody = "{\"username\": \"aaaaaaaa\", \"userType\": \"GENERAL\", \"userStat\": \"WITHDRAWAL\"}";
        MvcResult result = mockMvc.perform(put("/user/{id}", save.getId())
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
    @DisplayName("회원 수정 실패 : invalid username Pattern")
    void failUsernameUpdateCausedByInvalidPattern() throws Exception {
        // given
        User user = User.builder().username("aaaaaaaa")
                .userType(UserType.GENERAL).userStat(UserStat.ACCESSION).build();
        User save = userRepository.save(user);

        // when
        String requestBody = "{\"username\": \"1234\", \"userType\": \"GENERAL\"}";
        MvcResult result = mockMvc.perform(put("/user/{id}", save.getId())
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
    @DisplayName("회원 수정 실패 : invalid userId, user not found")
    void failUsernameUpdateCausedByNotFound() throws Exception {
        // given
        User user = User.builder().username("aaaaaaa")
                .userType(UserType.GENERAL).userStat(UserStat.ACCESSION).build();
        User save = userRepository.save(user);

        // when
        String requestBody = "{\"username\": \"aaachange\", \"userType\": \"GENERAL\"}";
        MvcResult result = mockMvc.perform(put("/user/{id}", 1234L)
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
