package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto getUser(int userId);

    UserDto editUser(int userId, UserDto userDto);

    boolean deleteUser(int userId);

    List<UserDto> getAllUsers();
}