package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.service.UnionService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UnionService unionService;

    @Transactional
    @Override
    public ResponseEntity<Object> add(BookingDto bookingDto, long userId) {
        unionService.checkItem(bookingDto.getItemId());
        Item item = itemRepository.findById(bookingDto.getItemId()).orElse(null);
        if (item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Вещь не найдена");
        }
        unionService.checkUser(userId);
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден");
        }
        Booking booking = BookingMapper.returnBooking(bookingDto);
        booking.setItem(item);
        booking.setBooker(user);
        if (item.getOwner().equals(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("владелец " + userId + " не может забронировать свою вещь");
        }
        if (!item.getAvailable()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Вещь " + item.getId() + " забронированая");
        }
        if (booking.getStart().isAfter(booking.getEnd())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Начало не может быть позднее конца");
        }
        if (booking.getStart().isEqual(booking.getEnd())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Начало не может быть равно концу");
        }
        bookingRepository.save(booking);
        return ResponseEntity.ok(BookingMapper.returnBookingDto(booking));
    }

    @Transactional
    @Override
    public BookingOutDto approve(long userId, long bookingId, Boolean approved) {
        unionService.checkBooking(bookingId);
        Booking booking = bookingRepository.findById(bookingId).get();
        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException(User.class, "Только владелец " + userId + " вещи может менять статус бронирования");
        }
        if (approved) {
            if (booking.getStatus().equals(Status.APPROVED)) {
                throw new ValidationException("Неверный запрос на обновление состояния");
            }
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        bookingRepository.save(booking);
        return BookingMapper.returnBookingDto(booking);
    }

    @Transactional(readOnly = true)
    @Override
    public BookingOutDto getById(long userId, long bookingId) {
        unionService.checkBooking(bookingId);
        Booking booking = bookingRepository.findById(bookingId).get();
        unionService.checkUser(userId);
        if (booking.getBooker().getId() == userId || booking.getItem().getOwner().getId() == userId) {
            return BookingMapper.returnBookingDto(booking);
        } else {
            throw new NotFoundException(User.class, "Для получения информации о бронировании или владельце предмета {} " + userId + "можно ипсользовать id владельца предмета");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutDto> getAllBookingsByBookerId(long userId, String state) {
        unionService.checkUser(userId);
        List<Booking> bookings = null;
        State bookingState = State.getEnumValue(state);
        switch (bookingState) {
            case ALL:
                bookings = bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
                break;
            case CURRENT:
                bookings = bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfterOrderByStartAsc(userId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findAllByBookerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository.findAllByBookerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case WAITING:
                bookings = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllByBookerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
                break;
        }
        return BookingMapper.returnBookingDtoList(bookings);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingOutDto> getAllBookingsForAllItemsByOwnerId(long userId, String state) {
        unionService.checkUser(userId);
        if (itemRepository.findByOwnerId(userId).isEmpty()) {
            throw new ValidationException("У пользователя нет вещей для бронирования");
        }
        List<Booking> bookings = null;
        State bookingState = State.getEnumValue(state);
        switch (bookingState) {
            case ALL:
                bookings = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(userId);
                break;
            case CURRENT:
                bookings = bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfterOrderByStartAsc(userId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case PAST:
                bookings = bookingRepository.findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case FUTURE:
                bookings = bookingRepository.findAllByItemOwnerIdAndStartAfterOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case WAITING:
                bookings = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.WAITING);
                break;
            case REJECTED:
                bookings = bookingRepository.findAllByItemOwnerIdAndStatusOrderByStartDesc(userId, Status.REJECTED);
                break;
        }
        return BookingMapper.returnBookingDtoList(bookings);
    }
}
