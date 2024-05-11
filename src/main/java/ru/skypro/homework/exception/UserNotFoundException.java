package ru.skypro.homework.exception;

public class UserNotFoundException extends NotFoundException{

    private final String email;

    public UserNotFoundException(String email) {
        this.email = email;
    }


    @Override
    public String getMessage() {
        return "User with email: " + email + " is not found";
    }
}
