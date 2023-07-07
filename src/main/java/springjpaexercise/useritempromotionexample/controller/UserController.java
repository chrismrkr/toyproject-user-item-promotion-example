package springjpaexercise.useritempromotionexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springjpaexercise.useritempromotionexample.entity.User;
import springjpaexercise.useritempromotionexample.entity.dto.ResponseDto;
import springjpaexercise.useritempromotionexample.entity.dto.UserDto;
import springjpaexercise.useritempromotionexample.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseDto createUser(@RequestBody UserDto userDto) {
        User savedUser = userService.save(userDto);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
    @PutMapping("/user")
    public void updateUser(@RequestBody UserDto userDto) {

    }
    @DeleteMapping("/user")
    public void deleteUser(@RequestBody UserDto userDto) {

    }

}
