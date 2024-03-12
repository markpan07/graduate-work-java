package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.RegisterDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;

import java.io.IOException;

public interface UserService {

    public void updatePassword(NewPasswordDto dto, String username);
    public UserDto getInfoAboutMe(String username);
    public UpdateUserDto updateInfoAboutMe(String username, UpdateUserDto dto);
    public void updateMyImage(String username, MultipartFile file) throws IOException;
    public ResponseEntity<User> registerUser(RegisterDto dto);




}
