package springjpaexercise.useritempromotionexample.unittest.promotion.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
import springjpaexercise.useritempromotionexample.entity.Promotion;
import springjpaexercise.useritempromotionexample.entity.PromotionItem;
import springjpaexercise.useritempromotionexample.entity.embeddable.DiscountPolicy;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;
import springjpaexercise.useritempromotionexample.repository.ItemRepository;
import springjpaexercise.useritempromotionexample.repository.PromotionItemRepository;
import springjpaexercise.useritempromotionexample.repository.PromotionRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SelectTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    PromotionItemRepository promotionItemRepository;

    @Test
    @DisplayName("1. 프로모션 조회 성공 : 프로모션이 1개만 존재하는 경우")
    void success1() throws Exception {
        // given
        Item item1 = Item.builder().itemName("item1").itemPrice(3000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Item save = itemRepository.save(item1);
        Promotion promotion1 = Promotion.builder().promotionName("promotion1")
                .discountPolicy(DiscountPolicy.builder().discountAmount(1000).build())
                .promotionDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        promotionRepository.save(promotion1);
        PromotionItem promotionItem = new PromotionItem(promotion1, item1);
        promotionItemRepository.save(promotionItem);
        // when
        MvcResult result = mockMvc.perform(get("/promotion/item/{itemId}", save.getId())
                        .contentType("application/json")
                        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("2. 프로모션 조회 성공 : 프로모션이 N개 존재하는 경우")
    void success2() throws Exception {
        // given
        Item item1 = Item.builder().itemName("item1").itemPrice(3000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Item save = itemRepository.save(item1);
        Promotion promotion1 = Promotion.builder().promotionName("promotion1")
                .discountPolicy(DiscountPolicy.builder().discountAmount(1000).build())
                .promotionDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Promotion promotion2 = Promotion.builder().promotionName("promotion2")
                .discountPolicy(DiscountPolicy.builder().discountAmount(1300).build())
                .promotionDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Promotion promotion3 = Promotion.builder().promotionName("promotion3")
                .discountPolicy(DiscountPolicy.builder().discountRate(0.6).build())
                .promotionDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        promotionRepository.save(promotion1);
        promotionRepository.save(promotion2);
        promotionRepository.save(promotion3);
        PromotionItem promotionItem1 = new PromotionItem(promotion1, item1);
        PromotionItem promotionItem2 = new PromotionItem(promotion2, item1);
        PromotionItem promotionItem3 = new PromotionItem(promotion3, item1);
        promotionItemRepository.save(promotionItem1);
        promotionItemRepository.save(promotionItem2);
        promotionItemRepository.save(promotionItem3);
        // when
        MvcResult result = mockMvc.perform(get("/promotion/item/{itemId}", save.getId())
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("3. 프로모션 조회 실패 : 프로모션이 없는 경우")
    void fail1() throws Exception {
        // given
        Item item1 = Item.builder().itemName("item1").itemPrice(3000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Item save = itemRepository.save(item1);
        Promotion promotion1 = Promotion.builder().promotionName("promotion1")
                .discountPolicy(DiscountPolicy.builder().discountAmount(1000).build())
                .promotionDate(new StartEndDate(
                        LocalDate.now().minusMonths(2),
                        LocalDate.now().minusMonths(1))
                )
                .build();
        promotionRepository.save(promotion1);
        // when
        MvcResult result = mockMvc.perform(get("/promotion/item/{itemId}", save.getId())
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @SneakyThrows
    @Test
    @DisplayName("4. 프로모션 조회 실패 : 프로모션 적용 시, 가격이 0 이하인 경우")
    void fail2() {
        // given
        Item item1 = Item.builder().itemName("item1").itemPrice(3000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.now(),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        Item save = itemRepository.save(item1);
        Promotion promotion1 = Promotion.builder().promotionName("promotion1")
                .discountPolicy(DiscountPolicy.builder().discountAmount(3000).build())
                .promotionDate(new StartEndDate(
                        LocalDate.now().minusDays(10),
                        LocalDate.now().plusMonths(1))
                )
                .build();
        promotionRepository.save(promotion1);
        PromotionItem promotionItem = new PromotionItem(promotion1, item1);
        promotionItemRepository.save(promotionItem);
        // when
        MvcResult result = mockMvc.perform(get("/promotion/item/{itemId}", save.getId())
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
}
