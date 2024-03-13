package ru.skypro.homework.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import ru.skypro.homework.dto.user.Role;

import javax.persistence.*;

@Data

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Value("${path.to.user.image}")
    private String image;


}
