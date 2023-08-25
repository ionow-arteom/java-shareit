package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;

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
    public ResponseEntity<BookingOutDto> add(@RequestHeader(USER_ID) Long userId,
                                      @RequestBody @Valid BookingDto bookingDto) {
        log.info("Пользователь {}, добавил новое бронирование", userId);
        return ResponseEntity.ok(bookingService.add(bookingDto, userId));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingOutDto> approve(@RequestHeader(USER_ID) Long userId,
                                     @PathVariable Long bookingId,
                                     @RequestParam Boolean approved) {
        log.info("Пользователь {}, изменил статус бронирования {}", userId, bookingId);
        return ResponseEntity.ok(bookingService.approve(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingOutDto> getById(@RequestHeader(USER_ID) Long userId,
                                                 @PathVariable Long bookingId) {
        log.info("Получить бронирование {}", bookingId);
        return ResponseEntity.ok(bookingService.getById(userId, bookingId));
    }

    @GetMapping
    public ResponseEntity<List<BookingOutDto>> getAllBookingsByBookerId(@RequestHeader(USER_ID) Long userId,
                                                                        @RequestParam(defaultValue = "ALL", required = false) String state,
                                                                        @RequestParam(defaultValue = "0", required = false) Integer from,
                                                                        @RequestParam(defaultValue = "10", required = false) Integer size) {
        log.info("Получить все бронирования от букера Id {}", userId);
        return ResponseEntity.ok(bookingService.getAllBookingsByBookerId(userId, state, from, size));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingOutDto>> getAllBookingsForAllItemsByOwnerId(@RequestHeader(USER_ID) Long userId,
                                                                                  @RequestParam(defaultValue = "ALL", required = false) String state,
                                                                                  @RequestParam(defaultValue = "0", required = false) Integer from,
                                                                                  @RequestParam(defaultValue = "10", required = false) Integer size) {
        log.info("Получить все заказы на все вещи по владельцу Id {}", userId);
        return ResponseEntity.ok(bookingService.getAllBookingsForAllItemsByOwnerId(userId, state, from, size));
    }
}

