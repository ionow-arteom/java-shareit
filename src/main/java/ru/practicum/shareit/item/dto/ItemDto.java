package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

@Data
@Builder
public class ItemDto {

    private Long id;
    @NotNull(message = "Имя не может быть пустым или содержать пробелы")
    @NotBlank(message = "Имя не может быть пустым или содержать пробелы")
    private String name;
    @NotNull(message = "Описание не может быть пустым или содержать пробелы")
    @NotBlank(message = "Описание не может быть пустым или содержать пробелы")
    private String description;
    @NotNull(message = "Поле не может быть пустым")
    private Boolean available;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
}