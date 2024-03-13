package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.User;
import java.lang.Integer;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   public  Optional<User> findByEmail(String email);



}
