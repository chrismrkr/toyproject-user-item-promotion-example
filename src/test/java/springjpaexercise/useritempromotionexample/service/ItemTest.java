package springjpaexercise.useritempromotionexample.service;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;
import springjpaexercise.useritempromotionexample.repository.ItemRepository;
import springjpaexercise.useritempromotionexample.repository.UserRepository;
import springjpaexercise.useritempromotionexample.service.impl.ItemServiceImpl;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
public class ItemTest {
    @Mock UserRepository userRepository;
    @Mock ItemRepository itemRepository;
    ItemService itemService;

    @Test
    @DisplayName("Item DisplayDate 조회 : userType이 Enterprise인 경우")
    void findItemByDateAndTypeEnterprise(){
        // given
        User user = User.builder().username("user1")
                        .userType(UserType.ENTERPRISE)
                                .userStat(UserStat.ACCESSION)
                                        .build();
        given(userRepository.findById(any()))
                .willReturn(Optional.ofNullable(user));

        given(itemRepository.findByDateBetweenStartEndDate(any()))
                .willReturn(createAllItemList());
        itemService = new ItemServiceImpl(itemRepository, userRepository);
        // when
        List<Item> itemList = itemService.findItemListByUserType(user.getId());
        // then
        assertEquals(4, itemList.size());
        for(Item i : itemList) {
            log.info("[item Type] " + i.getItemType() + " | [item Name] " + i.getItemName());
        }
    }
    @Test
    @DisplayName("Item DisplayDate 조회 : userType이 GENERAL인 경우")
    void findItemByDateAndTypeGeneral(){
        // given
        User user = User.builder().username("user1")
                .userType(UserType.GENERAL)
                .userStat(UserStat.ACCESSION)
                .build();
        given(userRepository.findById(any()))
                .willReturn(Optional.ofNullable(user));
        given(itemRepository.findByItemTypeAndByDateBetweenStartEndDate(ItemType.GENERAL, LocalDate.now()))
                .willReturn(createGeneralItemList());
        itemService = new ItemServiceImpl(itemRepository, userRepository);
        // when
        List<Item> itemList = itemService.findItemListByUserType(user.getId());
        // then
        assertEquals(2, itemList.size());
        for(Item i : itemList) {
            log.info("[item Type] " + i.getItemType() + " | [item Name] " + i.getItemName());
        }
    }
    @Test
    @DisplayName("Item DisplayDate 조회 실패 : userStat이 WITHDRAWAL인 경우")
    void findItemByDateAndTypeFail(){
        // given
        User user = User.builder().username("user1")
                .userType(UserType.GENERAL)
                .userStat(UserStat.WITHDRAWAL)
                .build();
        given(userRepository.findById(any()))
                .willReturn(Optional.ofNullable(user));
        // when-then
        List<Item> itemList = itemService.findItemListByUserType(user.getId());
    }


    List<Item> createAllItemList() {
        Item item1 = Item.builder().itemName("enterpriseItem1")
                .itemPrice(1000)
                .itemType(ItemType.ENTERPRISE)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023, 1, 1),
                        LocalDate.of(2023, 12, 12)
                )).build();
        Item item2 = Item.builder().itemName("enterpriseItem2")
                .itemPrice(1000)
                .itemType(ItemType.ENTERPRISE)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023, 1, 1),
                        LocalDate.of(2023, 12, 12)
                )).build();
        Item item3 = Item.builder().itemName("generalItem1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023, 1, 1),
                        LocalDate.of(2023, 12, 12)
                )).build();
        Item item4 = Item.builder().itemName("generalItem2")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023, 1, 1),
                        LocalDate.of(2023, 12, 12)
                )).build();
        List<Item> allItemList = new ArrayList<>();
        allItemList.add(item1);
        allItemList.add(item2);
        allItemList.add(item3);
        allItemList.add(item4);
        return allItemList;
    }
    List<Item> createGeneralItemList() {
        Item item5 = Item.builder().itemName("generalItem1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023, 1, 1),
                        LocalDate.of(2023, 12, 12)
                )).build();
        Item item6 = Item.builder().itemName("generalItem2")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023, 1, 1),
                        LocalDate.of(2023, 12, 12)
                )).build();
        List<Item> generalItemList = new ArrayList<>();
        generalItemList.add(item5);
        generalItemList.add(item6);
        return generalItemList;
    }
}
