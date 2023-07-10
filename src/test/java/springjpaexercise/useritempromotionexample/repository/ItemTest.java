package springjpaexercise.useritempromotionexample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemTest {
    @Autowired EntityManager em;
    @Autowired ItemRepository itemRepository;
    @Test
    @DisplayName("상품 생성 테스트")
    void createItem() {
        //given
        Item item1 = Item.builder().itemName("item1").itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        //when
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //then
        Item find = itemRepository.findById(save.getId())
                .orElseThrow(() -> new RuntimeException());
        assertEquals(save.getId(), find.getId());
    }
    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItem() {
        //given
        Item item1 = Item.builder().itemName("item1").itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //when
        Item find = itemRepository.findById(item1.getId()).get();
        itemRepository.delete(find);
        //then
        assertEquals(0, itemRepository.findAll().size());
    }

    @Test
    @DisplayName("상품 itemName 업데이트 테스트")
    void updateItemName() {
        //given
        Item item1 = Item.builder()
                .itemName("item1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //when
        Item find = itemRepository.findById(item1.getId()).get();
        find.updateItemName("item2");
        em.flush(); em.clear();
        //then
        assertEquals("item2",
                itemRepository.findById(save.getId()).get().getItemName());
    }
    @Test
    @DisplayName("상품 itemType 업데이트 테스트")
    void updateItemType() {
        //given
        Item item1 = Item.builder()
                .itemName("item1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //when
        Item find = itemRepository.findById(item1.getId()).get();
        find.updateItemType("ENTERPRISE");
        em.flush(); em.clear();
        //then
        assertEquals(ItemType.ENTERPRISE,
                itemRepository.findById(save.getId()).get().getItemType());
    }
    @Test
    @DisplayName("상품 itemPrice 업데이트 테스트")
    void updateItemPrice() {
        //given
        Item item1 = Item.builder()
                .itemName("item1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //when
        Item find = itemRepository.findById(item1.getId()).get();
        find.updateItemPrice(2000);
        em.flush(); em.clear();
        //then
        assertEquals(2000,
                itemRepository.findById(save.getId()).get().getItemPrice());
    }
    @Test
    @DisplayName("상품 startDate 업데이트 테스트")
    void updateStartDate() {
        //given
        Item item1 = Item.builder()
                .itemName("item1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //when
        Item find = itemRepository.findById(item1.getId()).get();
        find.updateStartDate("2023-01-01", DateTimeFormatter.ISO_LOCAL_DATE);
        em.flush(); em.clear();
        //then
        assertEquals(LocalDate.of(2023,1,1),
                itemRepository.findById(save.getId()).get().getItemDisplayDate().getStartDate());
    }
    @Test
    @DisplayName("상품 endDate 업데이트 테스트")
    void updateEndDate() {
        //given
        Item item1 = Item.builder()
                .itemName("item1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //when
        Item find = itemRepository.findById(item1.getId()).get();
        find.updateEndDate("2023-12-30", DateTimeFormatter.ISO_LOCAL_DATE);
        em.flush(); em.clear();
        //then
        assertEquals(LocalDate.of(2023,12,30),
                itemRepository.findById(save.getId()).get().getItemDisplayDate().getStartDate());
    }
    @Test
    @DisplayName("상품 조회 테스트 : between startDate-updateDate ")
    void selectItemBetweenStartDateAndEndDate() {
        //given
        Item item1 = Item.builder()
                .itemName("item1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,12,1),
                        LocalDate.of(2023,12,30)))
                .build();
        Item item2 = Item.builder()
                .itemName("item2")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,12,1),
                        LocalDate.of(2023,12,20)))
                .build();
        Item item3 = Item.builder()
                .itemName("item3")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,11,1),
                        LocalDate.of(2023,11,15)))
                .build();
        Item item4 = Item.builder()
                .itemName("item4")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,11,1),
                        LocalDate.of(2023,11,30)))
                .build();
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        // when - then
        assertEquals(1,
                itemRepository.findByDateBetweenStartEndDate(LocalDate.of(2023,12,25)).size());
        assertEquals(2,
                itemRepository.findByDateBetweenStartEndDate(LocalDate.of(2023,12,10)).size());
        assertEquals(2,
                itemRepository.findByDateBetweenStartEndDate(LocalDate.of(2023,11,1)).size());
        assertEquals(1,
                itemRepository.findByDateBetweenStartEndDate(LocalDate.of(2023,11,16)).size());
    }
    @Test
    @DisplayName("상품 조회 테스트 : between startDate-endDate & itemType")
    void selectItemBetweenStartDateAndEndDateAndItemType() {
        //given
        Item item1 = Item.builder()
                .itemName("item1")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,12,1),
                        LocalDate.of(2023,12,30)))
                .build();
        Item item2 = Item.builder()
                .itemName("item2")
                .itemPrice(1000)
                .itemType(ItemType.ENTERPRISE)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,12,1),
                        LocalDate.of(2023,12,20)))
                .build();
        Item item3 = Item.builder()
                .itemName("item3")
                .itemPrice(1000)
                .itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,11,1),
                        LocalDate.of(2023,11,15)))
                .build();
        Item item4 = Item.builder()
                .itemName("item4")
                .itemPrice(1000)
                .itemType(ItemType.ENTERPRISE)
                .itemDisplayDate(new StartEndDate(
                        LocalDate.of(2023,11,1),
                        LocalDate.of(2023,11,30)))
                .build();
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        // when - then
        assertEquals(1,
                itemRepository.findByItemTypeAndByDateBetweenStartEndDate(ItemType.ENTERPRISE, LocalDate.of(2023,12,10)).size());
        assertEquals(1,
                itemRepository.findByItemTypeAndByDateBetweenStartEndDate(ItemType.GENERAL, LocalDate.of(2023,11,1)).size());
    }
}
