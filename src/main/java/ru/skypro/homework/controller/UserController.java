package ru.skypro.homework.controller;

import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.dto.UserDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {



    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto dto) {
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser() {
        UserDto dto = new UserDto();
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDto> updateUser(@Valid @RequestBody UpdateUserDto dto) {
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/me/image")
    public ResponseEntity<Void> updateUserImage(MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
