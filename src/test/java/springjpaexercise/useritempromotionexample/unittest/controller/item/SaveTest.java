package springjpaexercise.useritempromotionexample.unittest.controller.item;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SaveTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    @DisplayName("상품 저장 성공")
    void saveSuccess() throws Exception {
        // given
        String requestBody = "{" +
                "\"itemName\": \"item1\", \"itemType\": \"GENERAL\", " +
                "\"itemPrice\": 10000, \"startDate\": \"2023-07-01\", "+
                "\"endDate\": \"2023-07-30\""+
                "}";
        // when
        MvcResult result = mockMvc.perform(post("/item")
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
    @DisplayName("상품 저장 실패 : 상품명 없음")
    void emptyItemName() throws Exception {
        // given
        String requestBody = "{" +
                "\"itemName\": \"\", \"itemType\": \"GENERAL\", " +
                "\"itemPrice\": 10000, \"startDate\": \"2023-07-01\", "+
                "\"endDate\": \"2023-07-30\""+
                "}";
        // when
        MvcResult result = mockMvc.perform(post("/item")
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
    @DisplayName("상품 저장 실패 : 시작일이 종료일보다 더 크다.")
    void startDateBiggerThanEndDate() throws Exception {
        // given
        String requestBody = "{" +
                "\"itemName\": \"item1\", \"itemType\": \"GENERAL\", " +
                "\"itemPrice\": 10000, \"startDate\": \"2023-07-20\", "+
                "\"endDate\": \"2023-07-10\""+
                "}";
        // when
        MvcResult result = mockMvc.perform(post("/item")
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
