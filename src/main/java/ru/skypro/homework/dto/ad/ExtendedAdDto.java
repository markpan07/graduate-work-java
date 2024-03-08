package ru.skypro.homework.dto.ad;

import lombok.Data;

@Data
public class ExtendedAdDto {
    private Integer pk;
    private Integer authorFirstName;
    private Integer authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;

}


