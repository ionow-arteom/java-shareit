package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;


public interface UserService {

    UserDto add(UserDto userDto);

    UserDto update(UserDto userDto, long userId);

    void delete(long userId);

    UserDto getById(long userId);

    List<UserDto> getAll();
}