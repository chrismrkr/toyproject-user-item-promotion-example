package springjpaexercise.useritempromotionexample.unittest.promotion.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;
import springjpaexercise.useritempromotionexample.repository.ItemRepository;
import springjpaexercise.useritempromotionexample.repository.PromotionItemRepository;
import springjpaexercise.useritempromotionexample.repository.PromotionRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class CreateTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    PromotionItemRepository promotionItemRepository;
    @Test
    @DisplayName("1. 프로모션 생성 성공")
    void success() throws Exception {
        // given
        Item item1 = Item.builder().itemName("item1").itemPrice(5000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Item save = itemRepository.save(item1);
        // when
        String requestBody = "{" +
                "\"itemId\": \"" + save.getId().toString() + "\"," +
                "\"promotionName\": \"PROMOTION1\", " +
                "\"discountAmount\": 1000, \"startDate\": \"2023-08-01\", "+
                "\"endDate\": \"2023-08-30\""+
                "}";
        MvcResult result = mockMvc.perform(post("/promotion")
                        .contentType("application/json")
                        .content(requestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
        Assertions.assertEquals(1, promotionRepository.findAll().size());
        Assertions.assertEquals(1, promotionItemRepository.findAll().size());
        Assertions.assertEquals(save.getId(), promotionItemRepository.findAll().get(0).getItem().getId());
    }

    @Test
    @DisplayName("2. 프로모션 생성 실패 : 상품이 존재하지 않는 경우")
    void fail1() throws Exception {
        // given - when
        String requestBody = "{" +
                "\"itemId\": \"" + "1" + "\"," +
                "\"promotionName\": \"PROMOTION1\", " +
                "\"discountAmount\": 1000, \"startDate\": \"2023-08-01\", "+
                "\"endDate\": \"2023-08-30\""+
                "}";
        MvcResult result = mockMvc.perform(post("/promotion")
                        .contentType("application/json")
                        .content(requestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }

    @Test
    @DisplayName("3. 프로모션 생성 실패 : 할인 정책이 설정되지 않음")
    void fail2() throws Exception {
        // given
        Item item1 = Item.builder().itemName("item1").itemPrice(5000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Item save = itemRepository.save(item1);
        // when
        String requestBody = "{" +
                "\"itemId\": \"" + save.getId().toString() + "\"," +
                "\"promotionName\": \"PROMOTION1\", " +
                "\"startDate\": \"2023-08-01\", "+
                "\"endDate\": \"2023-08-30\""+
                "}";
        MvcResult result = mockMvc.perform(post("/promotion")
                        .contentType("application/json")
                        .content(requestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }

    @Test
    @DisplayName("4. 프로모션 생성 실패 : 프로모션이 정액제, 정률제 둘 중 하나만 적용되어야 함")
    void fail3() throws Exception {
        // given
        Item item1 = Item.builder().itemName("item1").itemPrice(5000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Item save = itemRepository.save(item1);
        // when
        String requestBody = "{" +
                "\"itemId\": \"" + save.getId().toString() + "\"," +
                "\"discountAmount\": 1000, " +
                "\"discountRate\" : 0.2, " +
                "\"promotionName\": \"PROMOTION1\", " +
                "\"startDate\": \"2023-08-01\", "+
                "\"endDate\": \"2023-08-30\""+
                "}";
        MvcResult result = mockMvc.perform(post("/promotion")
                        .contentType("application/json")
                        .content(requestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
}
