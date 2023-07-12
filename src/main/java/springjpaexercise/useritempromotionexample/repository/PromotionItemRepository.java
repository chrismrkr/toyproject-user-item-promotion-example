package springjpaexercise.useritempromotionexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springjpaexercise.useritempromotionexample.entity.PromotionItem;

import java.time.LocalDate;
import java.util.List;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, PromotionItem.JoinTableId> {

    @Query("SELECT pi FROM PromotionItem pi " +
            "LEFT JOIN FETCH pi.item " +
            "JOIN FETCH pi.promotion " +
            "WHERE pi.item.id = :itemId AND "+
            "pi.promotion.promotionDate.endDate >= :date AND "+
            "pi.promotion.promotionDate.startDate <= :date")
    List<PromotionItem> findByItemId(@Param("itemId") Long itemId, @Param("date") LocalDate date);
}
