package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void returnUserDto() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        UserDto userDto = UserMapper.returnUserDto(user);

        assertThat(userDto.getId()).isEqualTo(1L);
        assertThat(userDto.getName()).isEqualTo("John");
        assertThat(userDto.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void returnUser() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        User user = UserMapper.returnUser(userDto);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void returnUserDtoList() {
        User user1 = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("Alice")
                .email("alice@example.com")
                .build();

        List<User> userList = List.of(user1, user2);

        List<UserDto> userDtoList = UserMapper.returnUserDtoList(userList);

        assertThat(userDtoList).hasSize(2);
        assertThat(userDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(userDtoList.get(0).getName()).isEqualTo("John");
        assertThat(userDtoList.get(0).getEmail()).isEqualTo("john@example.com");
        assertThat(userDtoList.get(1).getId()).isEqualTo(2L);
        assertThat(userDtoList.get(1).getName()).isEqualTo("Alice");
        assertThat(userDtoList.get(1).getEmail()).isEqualTo("alice@example.com");
    }
}