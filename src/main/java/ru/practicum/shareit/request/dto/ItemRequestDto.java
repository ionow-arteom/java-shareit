package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ItemRequestDto {
    private long id;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
    private LocalDateTime created;
    private List<ItemDto> items;
}