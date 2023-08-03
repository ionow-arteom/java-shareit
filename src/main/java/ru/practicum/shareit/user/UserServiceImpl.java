package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserDuplicateEmailException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final Map<Integer, UserDto> users = new HashMap<>();
    private int currentUserId = 1;

    @Override
    public UserDto addUser(UserDto userDto) {
        for (UserDto existingUser : users.values()) {
            if (existingUser.getEmail().equals(userDto.getEmail())) {
                throw new UserDuplicateEmailException();
            }
        }
        userDto.setId(currentUserId++);
        users.put(userDto.getId(), userDto);
        return userDto;
    }

    @Override
    public UserDto getUser(int userId) {
        return users.get(userId);
    }

    @Override
    public UserDto editUser(int userId, UserDto userDto) {
        UserDto existingUser = users.get(userId);
        if (existingUser != null) {
            String name = userDto.getName();
            String email = userDto.getEmail();

            for (UserDto user : users.values()) {
                if (user.getId() != userId && user.getEmail().equals(email)) {
                    throw new UserDuplicateEmailException();
                }
            }

            if (name == null || name.isEmpty()) {
                name = existingUser.getName();
            }
            if (email == null || email.isEmpty()) {
                email = existingUser.getEmail();
            }

            existingUser.setName(name);
            existingUser.setEmail(email);
            return existingUser;
        }
        return null;
    }

    @Override
    public boolean deleteUser(int userId) {
        return users.remove(userId) != null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}