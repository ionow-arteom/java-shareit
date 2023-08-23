package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.exception.ErrorResponse;
import ru.practicum.shareit.exception.ValidationException;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.service.Constant.USER_ID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Object> add(@RequestHeader(USER_ID) Long userId,
                                      @RequestBody @Valid BookingDto bookingDto) {
        log.info("Пользователь {}, добавил новое бронирование", userId);
        return bookingService.add(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<?> approve(@RequestHeader(USER_ID) Long userId,
                                     @PathVariable Long bookingId,
                                     @RequestParam Boolean approved) {
        log.info("Пользователь {}, изменил статус бронирования {}", userId, bookingId);
        try {
            BookingOutDto bookingOutDto = bookingService.approve(userId, bookingId, approved);
            return ResponseEntity.ok(bookingOutDto);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{bookingId}")
    public BookingOutDto getById(@RequestHeader(USER_ID) Long userId,
                                 @PathVariable Long bookingId) {
        log.info("Получить бронирование {}", bookingId);
        return bookingService.getById(userId, bookingId);
    }

    @GetMapping
    public List<BookingOutDto> getAllBookingsByBookerId(@RequestHeader(USER_ID) Long userId,
                                                        @RequestParam(defaultValue = "ALL", required = false) String state) {
        log.info("Получить все бронирования по Id {}", userId);
        return bookingService.getAllBookingsByBookerId(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingOutDto> getAllBookingsForAllItemsByOwnerId(@RequestHeader(USER_ID) Long userId,
                                                                  @RequestParam(defaultValue = "ALL", required = false) String state) {
        log.info("Получить все заказы на все товары по id {}", userId);
        return bookingService.getAllBookingsForAllItemsByOwnerId(userId, state);
    }
}
