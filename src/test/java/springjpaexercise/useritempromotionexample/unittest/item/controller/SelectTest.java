package springjpaexercise.useritempromotionexample.unittest.item.controller;

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
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;
import springjpaexercise.useritempromotionexample.repository.ItemRepository;
import springjpaexercise.useritempromotionexample.repository.UserRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SelectTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void createItem() {
        Item item1 = Item.builder().itemName("G_item1").itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.parse("2023-07-01"),
                        LocalDate.parse("2023-07-30")
                )).build();
        Item item2 = Item.builder().itemName("G_item2").itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.parse("2023-07-01"),
                        LocalDate.parse("2023-07-30")
                )).build();
        Item item3 = Item.builder().itemName("E_item1").itemPrice(1000).itemType(ItemType.ENTERPRISE)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.parse("2023-07-01"),
                        LocalDate.parse("2023-07-30")
                )).build();
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
    }

    @Test
    @DisplayName("상품 조회 성공 : 일반 회원의 상품 조회")
    void success1() throws Exception {
        // given
        User user = User.builder().username("user1")
                .userType(UserType.GENERAL).userStat(UserStat.ACCESSION)
                .build();
        User save = userRepository.save(user);

        // when
        MvcResult result = mockMvc.perform(get("/item")
                        .contentType("application/json")
                        .queryParam("userId", save.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("상품 조회 성공 : 기업 회원의 상품 조회")
    void success2() throws Exception {
        // given
        User user = User.builder().username("user1")
                .userType(UserType.ENTERPRISE).userStat(UserStat.ACCESSION)
                .build();

        User save = userRepository.save(user);

        // when
        MvcResult result = mockMvc.perform(get("/item")
                        .contentType("application/json")
                        .queryParam("userId", save.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("상품 조회 실패 : 탈퇴한 회원의 상품 조회")
    void fail1() throws Exception {
        // given
        User user = User.builder().username("user1")
                .userType(UserType.ENTERPRISE).userStat(UserStat.WITHDRAWAL)
                .build();
        User save = userRepository.save(user);

        // when
        MvcResult result = mockMvc.perform(get("/item")
                        .contentType("application/json")
                        .queryParam("userId", save.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
    @Test
    @DisplayName("상품 조회 실패 : 사용자 존재하지 않음")
    void fail2() throws Exception {
// given
        User user = User.builder().username("user1")
                .userType(UserType.GENERAL).userStat(UserStat.ACCESSION)
                .build();
        User save = userRepository.save(user);

        // when
        MvcResult result = mockMvc.perform(get("/item")
                        .contentType("application/json")
                        .queryParam("userId","111"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // then
        MockHttpServletResponse response = result.getResponse();
        log.info("[result] : {} ", response.getContentAsString());
    }
}
