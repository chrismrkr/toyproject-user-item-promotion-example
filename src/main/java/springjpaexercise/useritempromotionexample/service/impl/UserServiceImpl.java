package springjpaexercise.useritempromotionexample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserStat;
import springjpaexercise.useritempromotionexample.entity.enumtype.UserType;
import springjpaexercise.useritempromotionexample.repository.UserRepository;
import springjpaexercise.useritempromotionexample.service.UserService;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User save(UserDto userDto) {
        User newUser = User.builder().username(userDto.getUsername())
                .userType(UserType.valueOf(userDto.getUserType().toUpperCase()))
                .userStat(UserStat.valueOf(userDto.getUserStat().toUpperCase()))
                .build();
        try {
            User save = userRepository.save(newUser);
            return save;
        } catch (Exception e) {
            throw new IllegalStateException("USER SAVE FAIL");
        }
    }

    @Override
    @Transactional
    public User updateUsername(UserDto userDto, Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("USER NOT EXIST"));
        findUser.updateUsername(userDto.getUsername());
        return findUser;
    }

    @Override
    public User updateUserType(UserDto userDto, Long id) {
        return null;
    }

    @Override
    public User updateUserStat(UserDto userDto, Long id) {
        return null;
    }

}
