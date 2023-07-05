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
        // when
        Promotion save = promotionRepository.save(promotion1);
        em.flush(); em.clear();
        // then
        Promotion find = promotionRepository.findById(promotion1.getId()).get();
        Assertions.assertEquals(promotion1.getId(), find.getId());
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
        Assertions.assertEquals(0, promotionRepository.findAll().size());
    }
}
