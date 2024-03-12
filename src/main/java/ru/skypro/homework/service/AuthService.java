package ru.skypro.homework.service;

import ru.skypro.homework.dto.user.LoginDto;
import ru.skypro.homework.dto.user.RegisterDto;

public interface AuthService {
    boolean login(LoginDto loginDto);

    boolean register(RegisterDto registerDto);
}
