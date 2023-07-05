package springjpaexercise.useritempromotionexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springjpaexercise.useritempromotionexample.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
