package springjpaexercise.useritempromotionexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springjpaexercise.useritempromotionexample.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
