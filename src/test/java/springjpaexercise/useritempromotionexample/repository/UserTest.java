package springjpaexercise.useritempromotionexample.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class UserTest {
    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("사용자 생성 테스트")
    void createUser() {
        // given
        User user1 = User.builder().username("user1").userStat(UserStat.ACCESSION).userType(UserType.GENERAL).build();
        // when
        User saved = userRepository.save(user1);
        em.flush(); em.clear();
        // then
        Optional<User> byId = userRepository.findById(saved.getId());
        Assertions.assertEquals(saved.getId(), byId.get().getId());
    }
    @Test
    @DisplayName("사용자 삭제 테스트")
    void deleteUser() {
        // given
        User user1 = User.builder().username("user1").userStat(UserStat.ACCESSION).userType(UserType.GENERAL).build();
        User saved = userRepository.save(user1);
        em.flush(); em.clear();
        // when
        User find = userRepository.findById(user1.getId()).get();
        userRepository.delete(find);
        //then
        Assertions.assertEquals(0, userRepository.findAll().size());
    }
}
