package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MyUserDetails;
import ru.skypro.homework.config.MyUserDetailsService;
import ru.skypro.homework.dto.user.LoginDto;
import ru.skypro.homework.dto.user.RegisterDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserAlreadyRegisteredException;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsService myUserDetailService;
    private final PasswordEncoder encoder;
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    public AuthServiceImpl(MyUserDetailsService myUserDetailService,
                           PasswordEncoder passwordEncoder,
                           UserServiceImpl userService,
                           UserRepository userRepository) {
        this.myUserDetailService = myUserDetailService;
        this.encoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(LoginDto loginDto) {

        MyUserDetails userDetails = myUserDetailService.loadUserByUsername(loginDto.getUsername());
        return encoder.matches(loginDto.getPassword(), userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDto registerDto) {
        if(userRepository.findByEmail(registerDto.getUsername()).isPresent()) {
            return false;
          //  throw new UserAlreadyRegisteredException(registerDto.getUsername()); TODO:пробросить исключение в контроллер и обработать его статусом BAD_REQUEST
        } else {
            userService.registerUser(registerDto);
            return true;
        }
    }

}
