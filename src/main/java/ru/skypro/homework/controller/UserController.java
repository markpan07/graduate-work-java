package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.exception.PasswordIsNotCorrectException;
import ru.skypro.homework.service.impl.UserServiceImpl;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto dto, Authentication authentication) {
        try {
            userService.updatePassword(dto, authentication.getName());
        } catch (PasswordIsNotCorrectException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(dto);
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {

        return ResponseEntity.ok(userService.getInfoAboutMe(authentication.getName()));
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateUser(@Valid @RequestBody UpdateUserDto dto, Authentication authentication) {
        return ResponseEntity.ok(userService.updateInfoAboutMe(authentication.getName(), dto));
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateUserImage(MultipartFile image, Authentication authentication) throws IOException {
        userService.updateMyImage(authentication.getName(), image);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
