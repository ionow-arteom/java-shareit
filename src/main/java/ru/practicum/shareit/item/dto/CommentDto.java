package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    public Long id;
    @NotNull(message = "Поле не может быть пустым.")
    @NotBlank(message = "Поле не может быть пустым.")
    private String text;
    private LocalDateTime created;
    private String authorName;
}