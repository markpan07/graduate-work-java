package ru.skypro.homework.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public class Violation {

    private final String field;
    private final String message;

}
