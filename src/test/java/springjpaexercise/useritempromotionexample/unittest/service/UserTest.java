package springjpaexercise.useritempromotionexample.unittest.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;
import springjpaexercise.useritempromotionexample.repository.UserRepository;
import springjpaexercise.useritempromotionexample.service.UserService;
import springjpaexercise.useritempromotionexample.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Mock UserRepository userRepository;
    UserService userService;

    @Test
    @DisplayName("회원 생성 성공")
    void saveUser() {
        // given
        UserDto user1Dto = UserDto.builder().username("user1")
                .userType("ENTERPRISE")
                .userStat("ACCESSION").build();
        User user1 = User.builder().username(user1Dto.getUsername())
                .userType(UserType.valueOf(user1Dto.getUserType()))
                .userStat(UserStat.valueOf(user1Dto.getUserStat())).build();
        given(userRepository.save(user1)).willReturn(user1);
        userService = new UserServiceImpl(userRepository);
        //when
        User save = userService.save(user1Dto);
        //then
        assertEquals(user1.getUsername(), save.getUsername());
        assertEquals(user1.getUserType(), save.getUserType());
        assertEquals(user1.getUserStat(), save.getUserStat());
    }
}
