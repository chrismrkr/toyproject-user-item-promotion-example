package springjpaexercise.useritempromotionexample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.Promotion;
import springjpaexercise.useritempromotionexample.entity.PromotionItem;
import springjpaexercise.useritempromotionexample.entity.embeddable.DiscountPolicy;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PromotionItemTest {
    @Autowired EntityManager em;
    @Autowired PromotionRepository promotionRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired PromotionItemRepository promotionItemRepository;

    @BeforeEach
    void createItem() {
        Item item1 = Item.builder().itemName("item1").itemPrice(1000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Item item2 = Item.builder().itemName("item2").itemPrice(2000).itemType(ItemType.GENERAL)
                .itemDisplayDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        itemRepository.save(item1);
        itemRepository.save(item2);
        em.flush(); em.clear();
    }
    @BeforeEach
    void createPromotion() {
        Promotion promotion1 = Promotion.builder().promotionName("promotion1").discountPolicy(DiscountPolicy.builder().discountAmount(1000).build())
                .promotionDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Promotion promotion2 = Promotion.builder().promotionName("promotion2").discountPolicy(DiscountPolicy.builder().discountAmount(1500).build())
                .promotionDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .build();
        Promotion promotion3 = Promotion.builder().promotionName("promotion2").discountPolicy(DiscountPolicy.builder().discountAmount(1500).build())
                .promotionDate(new StartEndDate(LocalDate.now().minusMonths(2), LocalDate.now().minusMonths(1)))
                .build();
        promotionRepository.save(promotion1);
        promotionRepository.save(promotion2);
        promotionRepository.save(promotion3);
        em.flush(); em.clear();
    }

    @Test
    @DisplayName("PromotionItem 생성")
    void applyPromotionItem() {
        /* 상품0-프로모션0, 상품1-프로모션0,1 */
        // given
        List<Promotion> promotionList = promotionRepository.findAll();
        List<Item> itemList = itemRepository.findAll();
        PromotionItem promotionItem1 = new PromotionItem(promotionList.get(0), itemList.get(0));
        PromotionItem promotionItem2 = new PromotionItem(promotionList.get(0), itemList.get(1));
        PromotionItem promotionItem3 = new PromotionItem(promotionList.get(1), itemList.get(1));
        // when
        promotionItemRepository.save(promotionItem1);
        promotionItemRepository.save(promotionItem2);
        promotionItemRepository.save(promotionItem3);
        em.flush(); em.clear();
        // then
        List<Promotion> promotionList2 = promotionRepository.findAll();
        List<Item> itemList2 = itemRepository.findAll();
        assertEquals(3, promotionItemRepository.findAll().size());
        assertEquals(1, itemList2.get(0).getPromotionItems().size());
        assertEquals(2, itemList2.get(1).getPromotionItems().size());
        assertEquals(2, promotionList2.get(0).getPromotionItems().size());
        assertEquals(1, promotionList2.get(1).getPromotionItems().size());
    }
    @Test
    @DisplayName("PromotionItem 조회")
    void showPromotionItem() {
        // given
        List<Promotion> promotionList = promotionRepository.findAll();
        List<Item> itemList = itemRepository.findAll();
        PromotionItem promotionItem1 = new PromotionItem(promotionList.get(0), itemList.get(0));
        promotionItemRepository.save(promotionItem1);
        em.flush(); em.clear();
        // when
        List<Promotion> promotionList2 = promotionRepository.findAll();
        List<Item> itemList2 = itemRepository.findAll();
        PromotionItem promotionItem = promotionItemRepository.findById(
                PromotionItem.JoinTableId.builder()
                        .promotionPK(promotionList2.get(0).getId())
                        .itemPK(itemList2.get(0).getId()).build()
        ).get();
        // then
        assertNotNull(promotionItem);
    }
    @Test
    @DisplayName("PromotionItem 삭제")
    void deletePromotionItem() {
        // given
        List<Promotion> promotionList = promotionRepository.findAll();
        List<Item> itemList = itemRepository.findAll();
        PromotionItem promotionItem1 = new PromotionItem(promotionList.get(0), itemList.get(0));
        promotionItemRepository.save(promotionItem1);
        em.flush(); em.clear();
        // when
        List<Promotion> promotionList2 = promotionRepository.findAll();
        List<Item> itemList2 = itemRepository.findAll();
        promotionItemRepository.deleteById(PromotionItem.JoinTableId.builder()
                .promotionPK(promotionList2.get(0).getId())
                .itemPK(itemList2.get(0).getId()).build());
        // then
        assertEquals(0, promotionItemRepository.findAll().size());
    }

    @Test
    @DisplayName("Item 삭제 --|cascade|--> 연관 PromotionItem 삭제")
    void itemDeleteCascade() {
        // given
        List<Promotion> promotionList = promotionRepository.findAll();
        List<Item> itemList = itemRepository.findAll();
        PromotionItem promotionItem1 = new PromotionItem(promotionList.get(0), itemList.get(0));
        PromotionItem promotionItem2 = new PromotionItem(promotionList.get(0), itemList.get(1));
        PromotionItem promotionItem3 = new PromotionItem(promotionList.get(1), itemList.get(1));
        promotionItemRepository.save(promotionItem1);
        promotionItemRepository.save(promotionItem2);
        promotionItemRepository.save(promotionItem3);
        em.flush(); em.clear();
        // when : 상품 삭제
        Item deleteItem = itemRepository.findAll().get(1);
        itemRepository.delete(deleteItem);
        // then
        assertEquals(1, promotionItemRepository.findAll().size());
    }
    @Test
    @DisplayName("Promotiom 삭제 --|cascade|--> 연관 PromotionItem 삭제")
    void promotionDeleteCascade() {
        // given
        List<Promotion> promotionList = promotionRepository.findAll();
        List<Item> itemList = itemRepository.findAll();
        PromotionItem promotionItem1 = new PromotionItem(promotionList.get(0), itemList.get(0));
        PromotionItem promotionItem2 = new PromotionItem(promotionList.get(0), itemList.get(1));
        PromotionItem promotionItem3 = new PromotionItem(promotionList.get(1), itemList.get(1));
        promotionItemRepository.save(promotionItem1);
        promotionItemRepository.save(promotionItem2);
        promotionItemRepository.save(promotionItem3);
        em.flush(); em.clear();
        // when : 프로모션 삭제
        Promotion deletePromotion = promotionRepository.findAll().get(0);
        promotionRepository.delete(deletePromotion);
        // then
        assertEquals(1, promotionItemRepository.findAll().size());
    }
    @Test
    @DisplayName("PromotionItem 조회 : item과 연관된 PromotionItem 조회")
    void selectPromotionItemByItemId() {
        // given
        List<Promotion> promotionList = promotionRepository.findAll();
        List<Item> itemList = itemRepository.findAll();
        PromotionItem promotionItem1 = new PromotionItem(promotionList.get(0), itemList.get(0));
        PromotionItem promotionItem2 = new PromotionItem(promotionList.get(0), itemList.get(1));
        PromotionItem promotionItem3 = new PromotionItem(promotionList.get(1), itemList.get(1));
        PromotionItem promotionItem4 = new PromotionItem(promotionList.get(2), itemList.get(1));
        promotionItemRepository.save(promotionItem1);
        promotionItemRepository.save(promotionItem2);
        promotionItemRepository.save(promotionItem3);
        promotionItemRepository.save(promotionItem4);
        em.flush(); em.clear();
        // when
        List<PromotionItem> byItemId = promotionItemRepository.findByItemId(itemList.get(1).getId(), LocalDate.now());
        // then
        assertEquals(2, byItemId.size());
    }
}
