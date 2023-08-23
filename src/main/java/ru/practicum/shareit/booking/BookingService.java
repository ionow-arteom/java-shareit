package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;

import java.util.List;

public interface BookingService {

    ResponseEntity<Object> add(BookingDto bookingDto, long userId);

    BookingOutDto approve(long userId, long bookingId, Boolean approved);

    BookingOutDto getById(long userId, long bookingId);

    List<BookingOutDto> getAllBookingsByBookerId(long userId, String state);

    List<BookingOutDto> getAllBookingsForAllItemsByOwnerId(long userId, String state);
}
