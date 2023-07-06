package springjpaexercise.useritempromotionexample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import springjpaexercise.useritempromotionexample.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
}
