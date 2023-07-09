package springjpaexercise.useritempromotionexample.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.repository.UserRepository;
import springjpaexercise.useritempromotionexample.service.impl.UserServiceImpl;

import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTest {
    @Autowired UserRepository userRepository;
    UserService userService;
    @BeforeEach
    void injectRepository() {
        userService = new UserServiceImpl(userRepository);
    }


}
