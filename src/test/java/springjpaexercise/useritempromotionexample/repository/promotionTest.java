package springjpaexercise.useritempromotionexample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.Promotion;
import springjpaexercise.useritempromotionexample.entity.embeddable.DiscountPolicy;
import springjpaexercise.useritempromotionexample.entity.embeddable.StartEndDate;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class promotionTest {
    @Autowired EntityManager em;
    @Autowired PromotionRepository promotionRepository;

    @Test
    @DisplayName("프로모션 생성 테스트")
    void createPromotion() {
        // given
        Promotion promotion1 = Promotion.builder().promotionName("promotion1")
                .promotionDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .discountPolicy(DiscountPolicy.builder().discountAmount(1000).build())
                .build();
        Promotion promotion2 = Promotion.builder().promotionName("promotion2")
                .promotionDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .discountPolicy(DiscountPolicy.builder().discountRate(0.1).build())
                .build();
        // when
        Promotion save1 = promotionRepository.save(promotion1);
        Promotion save2 = promotionRepository.save(promotion2);
        em.flush(); em.clear();
        // then
        Promotion find1 = promotionRepository.findById(promotion1.getId()).get();
        assertEquals(promotion1.getId(), find1.getId());
        assertEquals(promotion1.getDiscountPolicy().getType(),
                                    find1.getDiscountPolicy().getType());
        assertEquals(promotion1.getDiscountPolicy().getDiscountAmount(),
                find1.getDiscountPolicy().getDiscountAmount());
        Promotion find2 = promotionRepository.findById(promotion2.getId()).get();
        assertEquals(promotion2.getId(), find2.getId());
        assertEquals(promotion2.getDiscountPolicy().getType(),
                find2.getDiscountPolicy().getType());
        assertEquals(promotion2.getDiscountPolicy().getDiscountAmount(),
                find2.getDiscountPolicy().getDiscountAmount());
    }

    @Test
    @DisplayName("프로모션 삭제 테스트")
    void deletePromotion() {
        // given
        Promotion promotion1 = Promotion.builder().promotionName("promotion1")
                .promotionDate(new StartEndDate(LocalDate.now(), LocalDate.now().plusMonths(1)))
                .discountPolicy(DiscountPolicy.builder().discountAmount(1000).build())
                .build();
        Promotion save = promotionRepository.save(promotion1);
        em.flush(); em.clear();
        // when
        Optional<Promotion> find = promotionRepository.findById(promotion1.getId());
        promotionRepository.delete(find.get());
        // then
        assertEquals(0, promotionRepository.findAll().size());
    }
}
