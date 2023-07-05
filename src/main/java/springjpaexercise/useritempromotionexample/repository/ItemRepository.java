package springjpaexercise.useritempromotionexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springjpaexercise.useritempromotionexample.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
