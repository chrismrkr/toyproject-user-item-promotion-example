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
    @PutMapping("/user/{id}")
    public ResponseDto updateUser(@RequestBody UserDto userDto, @PathVariable(name = "id") Long id) {
        User update = userService.update(userDto, id);
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
    @DeleteMapping("/user/{id}")
    public ResponseDto withdrawUser(@PathVariable(name = "id") Long id) {
        UserDto withdrawal = UserDto.builder().userStat("WITHDRAWAL").build();
        User update = userService.update(withdrawal, id);
        ResponseDto responseDto = new ResponseDto();
        return  responseDto;
    }

}
