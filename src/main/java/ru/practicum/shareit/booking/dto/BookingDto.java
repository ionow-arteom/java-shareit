package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.Status;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long itemId;
    @NotNull(message = "Поле не может быть пустым")
    @FutureOrPresent(message = "Может быть только в будущем.")
    private LocalDateTime start;
    @NotNull(message = "Поле не может быть пустым")
    @Future(message = "Может быть только в будущем")
    private LocalDateTime end;
    private Status status;
}
