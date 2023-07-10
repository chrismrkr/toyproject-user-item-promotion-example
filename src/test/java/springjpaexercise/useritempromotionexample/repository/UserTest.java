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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTest {
    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("User 생성 테스트")
    void createUser() {
        // given
        User user1 = User.builder().username("user1").userStat(UserStat.ACCESSION).userType(UserType.GENERAL).build();
        // when
        User saved = userRepository.save(user1);
        em.flush(); em.clear();
        // then
        Optional<User> byId = userRepository.findById(saved.getId());
        assertEquals(saved.getId(), byId.get().getId());
    }
    @Test
    @DisplayName("User 삭제 테스트")
    void deleteUser() {
        // given
        User user1 = User.builder().username("user1").userStat(UserStat.ACCESSION).userType(UserType.GENERAL).build();
        User saved = userRepository.save(user1);
        em.flush(); em.clear();
        // when
        User find = userRepository.findById(user1.getId()).get();
        userRepository.delete(find);
        //then
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    @DisplayName("User username 업데이트 테스트")
    void updateUsername() {
        //given
        User user1 = User.builder().username("user1").userStat(UserStat.ACCESSION).userType(UserType.GENERAL).build();
        User saved = userRepository.save(user1);
        em.flush(); em.clear();
        //when
        User find = userRepository.findById(user1.getId()).get();
        find.updateUsername("user2");
        em.flush(); em.clear();
        //then
        assertEquals(
                "user2",
                userRepository.findById(find.getId()).get().getUsername()
            );
    }
    @Test
    @DisplayName("User userStat 업데이트 테스트")
    void updateUserStat() {
        //given
        User user1 = User.builder().username("user1")
                .userStat(UserStat.ACCESSION)
                .userType(UserType.GENERAL).build();
        User saved = userRepository.save(user1);
        em.flush(); em.clear();
        //when
        User find = userRepository.findById(user1.getId()).get();
        find.updateUserStat("WITHDRAWAL");
        em.flush(); em.clear();
        //then
        assertEquals(UserStat.WITHDRAWAL,
                userRepository.findById(find.getId()).get().getUserStat());
    }
    @Test
    @DisplayName("User userType 업데이트 테스트")
    void updateUserType() {
        //given
        User user1 = User.builder().username("user1")
                .userStat(UserStat.ACCESSION)
                .userType(UserType.GENERAL).build();
        User saved = userRepository.save(user1);
        em.flush(); em.clear();
        //when
        User find = userRepository.findById(user1.getId()).get();
        find.updateUserType("ENTERPRISE");
        em.flush(); em.clear();
        //then
        assertEquals(UserType.ENTERPRISE, userRepository.findById(find.getId())
                .get().getUserType());
    }
}
