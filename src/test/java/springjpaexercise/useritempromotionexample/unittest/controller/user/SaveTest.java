package springjpaexercise.useritempromotionexample.unittest.controller.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springjpaexercise.useritempromotionexample.controller.UserController;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.repository.UserRepository;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SaveTest {
    @Autowired
    UserController userController;

    @Test
    @DisplayName("회원 저장 성공")
    void saveTest() {
        //given
        UserDto userDto = UserDto.builder().username("user1")
                .userType("GENERAL").build();
        //when
        UserDto responseDto = userController.createUser(userDto);
        // then
        assertEquals(userDto.getUsername(), responseDto.getUsername());
        assertEquals(userDto.getUserType(), responseDto.getUserType());
        assertEquals(UserStat.ACCESSION.toString(), responseDto.getUserStat());
    }
}
