package springjpaexercise.useritempromotionexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;
import springjpaexercise.useritempromotionexample.service.UserService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        User savedUser = userService.save(userDto);
        UserDto responseDto = createResponseUserDto(savedUser);
        return responseDto;
    }
    @PutMapping("/user/{id}")
    public UserDto updateUser(@Valid @RequestBody UserDto userDto, @PathVariable(name = "id") Long id) {
        User updatedUser = userService.update(userDto, id);
        UserDto responseDto = createResponseUserDto(updatedUser);
        return responseDto;
    }
    @DeleteMapping("/user/{id}")
    public UserDto withdrawUser(@PathVariable(name = "id") Long id) {
        UserDto withdrawal = UserDto.builder().userStat("WITHDRAWAL").build();
        User updatedUser = userService.update(withdrawal, id);
        UserDto responseDto = createResponseUserDto(updatedUser);
        return responseDto;
    }

    private UserDto createResponseUserDto(User user) {
        UserDto responseDto = UserDto.builder()
                .username(user.getUsername())
                .userType(user.getUserType().toString())
                .userStat(user.getUserStat().toString())
                .build();
        return responseDto;
    }
}
