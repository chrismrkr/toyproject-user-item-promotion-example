package springjpaexercise.useritempromotionexample.unittest.promotion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import springjpaexercise.useritempromotionexample.repository.PromotionItemRepository;
import springjpaexercise.useritempromotionexample.service.PromotionService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PromotionTest {
    @Mock PromotionItemRepository promotionItemRepository;
    PromotionService promotionService;
    @Test
    @DisplayName("promotionItem 조회 : 적용된 프로모션이 존재")
    void findPromotionItem() {

    }
    @Test
    @DisplayName("PromotionItem 조회 : 적용된 프로모션이 없음")
    void promotionNotExists() {

    }
    @Test
    @DisplayName("PromotionItem 조회 : 최소 할인가격이 0보다 작거나 같은 경우")
    void promotionItemPriceUnder0() {

    }
    @Test
    @DisplayName("PromotionItem 조회 : 모든 할인가격이 0보다 작은 경우")
    void allPromotionItemPriceUnder0() {

    }


}
