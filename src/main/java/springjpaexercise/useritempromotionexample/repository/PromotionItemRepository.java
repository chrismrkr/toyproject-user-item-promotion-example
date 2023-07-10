package springjpaexercise.useritempromotionexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springjpaexercise.useritempromotionexample.entity.PromotionItem;

import java.util.List;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, PromotionItem.JoinTableId> {

    @Query("SELECT pi FROM PromotionItem pi " +
            "LEFT JOIN FETCH pi.item " +
            "JOIN FETCH pi.promotion " +
            "WHERE pi.item.id = :itemId")
    List<PromotionItem> findByItemId(@Param("itemId") Long itemId);
}
