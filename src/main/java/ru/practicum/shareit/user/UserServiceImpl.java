package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.EmailExistException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.service.UnionService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UnionService unionService;

    @Transactional
    @Override
    public UserDto add(UserDto userDto) {
        User user = UserMapper.returnUser(userDto);
        userRepository.save(user);
        return UserMapper.returnUserDto(user);
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto, long userId) {
        User user = UserMapper.returnUser(userDto);
        user.setId(userId);
        unionService.checkUser(userId);
        User newUser = userRepository.findById(userId).get();
        if (user.getName() != null) {
            newUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            List<User> findEmail = userRepository.findByEmail(user.getEmail());
            if (!findEmail.isEmpty() && findEmail.get(0).getId() != userId) {
                throw new EmailExistException("уже есть пользователь с такой электронной почтой " + user.getEmail());
            }
            newUser.setEmail(user.getEmail());
        }
        userRepository.save(newUser);
        return UserMapper.returnUserDto(newUser);
    }

    @Transactional
    @Override
    public void delete(long userId) {
        unionService.checkUser(userId);
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getById(long userId) {
        unionService.checkUser(userId);
        return UserMapper.returnUserDto(userRepository.findById(userId).get());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAll() {
        return UserMapper.returnUserDtoList(userRepository.findAll());
    }
}