package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.BookingShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.util.List;

@Data
@Builder
public class ItemDto {

    private Long id;
    @NotBlank(message = "Имя не может быть пустым или содержать пробелы")
    private String name;
    @NotBlank(message = "Описание не может быть пустым или содержать пробелы")
    private String description;
    @NotNull(message = "Поле не может быть пустым")
    private Boolean available;
    private BookingShortDto lastBooking;
    private BookingShortDto nextBooking;
    private List<CommentDto> comments;
    @Positive(message = "значение должно быть положительным")
    private Long requestId;
}