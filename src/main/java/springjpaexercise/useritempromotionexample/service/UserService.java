package springjpaexercise.useritempromotionexample.service;

import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;

public interface UserService {
    User save(UserDto userDto);
    User update(UserDto userDto, Long id);
}
