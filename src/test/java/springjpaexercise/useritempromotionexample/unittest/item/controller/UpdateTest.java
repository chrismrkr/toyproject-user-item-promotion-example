package springjpaexercise.useritempromotionexample.unittest.item.controller;

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
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;
import springjpaexercise.useritempromotionexample.repository.ItemRepository;
import springjpaexercise.useritempromotionexample.repository.UserRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UpdateTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 수정 성공 : 상품명, 가격 수정")
    void updateSuccess() throws Exception {
        // given
        Item item = Item.builder().itemName("item1")
                .itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.parse("2023-07-01"),
                        LocalDate.parse("2023-07-30")
                        )).build();
        Item save = itemRepository.save(item);

        // when
        String requestBody = "{" +
                "\"itemName\": \"item2\", \"itemType\": \"GENERAL\", " +
                "\"itemPrice\": 2000, \"startDate\": \"2023-07-01\", "+
                "\"endDate\": \"2023-07-30\""+
                "}";
        MvcResult result = mockMvc.perform(put("/item/{id}", save.getId())
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
    @DisplayName("상품 수정 실패 : endDate 조정 실패")
    void updateEndDateFail() throws Exception {
        // given
        Item item = Item.builder().itemName("item1")
                .itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.parse("2023-07-01"),
                        LocalDate.parse("2023-07-30")
                )).build();
        Item save = itemRepository.save(item);

        // when
        String requestBody = "{" +
                "\"itemName\": \"item2\", \"itemType\": \"GENERAL\", " +
                "\"itemPrice\": 2000, \"startDate\": \"2023-07-01\", "+
                "\"endDate\": \"2023-06-30\""+
                "}";
        MvcResult result = mockMvc.perform(put("/item/{id}", save.getId())
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
