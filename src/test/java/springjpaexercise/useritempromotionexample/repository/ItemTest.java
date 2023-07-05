package springjpaexercise.useritempromotionexample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import javax.persistence.EntityManager;
import java.time.LocalDate;

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
                .itemDisplayStartDate(LocalDate.now())
                .itemDisplayEndDate(LocalDate.now().plusMonths(1)).build();
        //when
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //then
        Item find = itemRepository.findById(save.getId())
                .orElseThrow(() -> new RuntimeException());
        Assertions.assertEquals(save.getId(), find.getId());
    }
    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItem() {
        //given
        Item item1 = Item.builder().itemName("item1").itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayStartDate(LocalDate.now())
                .itemDisplayEndDate(LocalDate.now().plusMonths(1)).build();
        Item save = itemRepository.save(item1);
        em.flush(); em.clear();
        //when
        Item find = itemRepository.findById(item1.getId()).get();
        itemRepository.delete(find);
        //then
        Assertions.assertEquals(0, itemRepository.findAll().size());
    }
}
