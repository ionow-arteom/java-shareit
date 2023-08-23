package ru.practicum.shareit.user.dto;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotNull(message = "Логин не может быть пустым или содержать пробелы.")
    @NotBlank(message = "Логин не может быть пустым или содержать пробелы.")
    private String name;
    @NotNull(message = "Email не может быть пустым.")
    @NotBlank(message = "Email не может быть пустым.")
    @Email(message = "Не соответствует email-формату")
    private String email;
}