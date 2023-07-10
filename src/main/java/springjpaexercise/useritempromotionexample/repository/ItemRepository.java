package springjpaexercise.useritempromotionexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springjpaexercise.useritempromotionexample.entity.Item;
import springjpaexercise.useritempromotionexample.entity.enumtype.ItemType;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT i FROM Item i " +
            "WHERE i.itemDisplayDate.startDate <= :date AND "+
            "i.itemDisplayDate.endDate >= :date")
    List<Item> findByDateBetweenStartEndDate(@Param("date") LocalDate date);
    @Query(value = "SELECT i FROM Item i " +
            "WHERE i.itemDisplayDate.startDate <= :date AND "+
            "i.itemDisplayDate.endDate >= :date AND "+
            "i.itemType = :itemType")
    List<Item> findByItemTypeAndByDateBetweenStartEndDate(@Param("itemType") ItemType itemType,
                                                          @Param("date") LocalDate date);
}
