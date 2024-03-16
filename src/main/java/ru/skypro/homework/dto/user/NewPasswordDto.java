package ru.skypro.homework.dto.user;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class NewPasswordDto {
    @Size(min = 8,max = 16)
    private String currentPassword;

    @Size(min = 8,max = 16)
    private String newPassword;


}
