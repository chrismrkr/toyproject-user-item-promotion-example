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
                .userStat(UserStat.ACCESSION)
                .build();
        try {
            User save = userRepository.save(newUser);
            return save;
        } catch (Exception e) {
            throw new IllegalStateException("["+e.getMessage()+"]" + "USER SAVE FAIL : DB ERROR");
        }
    }

    @Override
    @Transactional
    public User update(UserDto userDto, Long id) {
        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("USER NOT EXIST"));
        if(userDto.getUsername() != null) {
            findUser.updateUsername(userDto.getUsername());
        }
        if(userDto.getUserType() != null) {
            findUser.updateUserType(userDto.getUserType());
        }
        if(userDto.getUserStat() != null) {
            findUser.updateUserStat(userDto.getUserStat());
        }
        return findUser;
    }

}
