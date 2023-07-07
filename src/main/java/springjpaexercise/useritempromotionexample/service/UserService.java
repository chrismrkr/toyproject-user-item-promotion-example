package springjpaexercise.useritempromotionexample.service;

import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;

public interface UserService {
    User save(UserDto userDto);
    User updateUsername(UserDto userDto, Long id);
    User updateUserType(UserDto userDto, Long id);
    User updateUserStat(UserDto userDto, Long id);
}
