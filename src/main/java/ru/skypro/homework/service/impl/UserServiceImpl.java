package ru.skypro.homework.service.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.user.NewPasswordDto;
import ru.skypro.homework.dto.user.RegisterDto;
import ru.skypro.homework.dto.user.UpdateUserDto;
import ru.skypro.homework.dto.user.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.PasswordIsNotCorrectException;
import ru.skypro.homework.exception.UserAlreadyRegisteredException;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private PasswordEncoder encoder;
    @Value("${path.to.user.images}")
    private String imagePath;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    @Override
    public void updatePassword(NewPasswordDto dto, String username) throws PasswordIsNotCorrectException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        if (encoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            user.setPassword(encoder.encode(dto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new PasswordIsNotCorrectException();
        }


    }

    @Override
    public byte[] getImage(String username) {
        try {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username is not found"));
            return Files.readAllBytes(Path.of(user.getImage()));
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: сделать exception
        }
    }

    @Override
    public UserDto getInfoAboutMe(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User is not found")); //TODO: возможно тут никогда не будет исключения
        return userMapper.toDto(user);
    }

    @Override
    public UpdateUserDto updateInfoAboutMe(String username, UpdateUserDto dto) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User is not found")); //TODO: возможно тут никогда не будет исключения
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        userRepository.save(user);
        return dto;
    }

    @Override
    public void updateMyImage(String username, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        uploadImage(user, file);
    }


    @Override
    public User registerUser(RegisterDto dto) {
        if (userRepository.findByEmail(dto.getUsername()).isPresent()) {
            throw new UserAlreadyRegisteredException(dto.getUsername());
        } else {
            User user = userMapper.toEntity(dto);
            user.setPassword(encoder.encode(dto.getPassword()));
            return userRepository.save(user);
        }
    }

    public void uploadImage(User user, MultipartFile file) throws IOException {
        Path path = Path.of(imagePath, user.getEmail() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename()));


        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
            user.setImage(path.toString());
            userRepository.save(user);
        }
    }

}
