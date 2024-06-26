package ru.skypro.homework.mapper;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.user.RegisterDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;

@Service
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setImage("/users/" + user.getEmail() + "/image");
        //dto.setImage(user.getImage());
        dto.setRole(user.getRole());
        return dto;
    }


    
    public User toEntity(RegisterDto dto) {
        User user = new User();
        user.setEmail(dto.getUsername().toLowerCase());
        user.setPhone(dto.getPhone());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());
        return user;
    }

}
