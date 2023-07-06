package springjpaexercise.useritempromotionexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springjpaexercise.useritempromotionexample.entity.PromotionItem;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, PromotionItem.JoinTableId> {

}
