package ru.practicum.shareit.user;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class User {
    private int id;
    private String name;
    @Email(message = "Введенное значение не является адресом электронной почты.")
    private String email;
}
