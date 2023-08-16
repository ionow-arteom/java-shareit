package ru.practicum.shareit.user;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping
    public UserDto add(@RequestBody @Valid UserDto userDto) {
        log.info("Добавлен пользователь {} ", userDto.getId());
        return userService.add(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@RequestBody UserDto userDto, @PathVariable Long userId) {
        log.info("Обновление пользователя {} ", userDto.getId());
        return userService.update(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info("Пользователь {} удален ", userId);
        userService.delete(userId);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        log.info("Получить пользователя {} ", userId);
        return userService.getById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("Список всех пользователей");
        return userService.getAll();
    }
}
