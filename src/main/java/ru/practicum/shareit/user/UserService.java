package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto add(UserDto userDto);

    UserDto get(int userId);

    UserDto edit(int userId, UserDto userDto);

    boolean delete(int userId);

    List<UserDto> getAllUsers();
}