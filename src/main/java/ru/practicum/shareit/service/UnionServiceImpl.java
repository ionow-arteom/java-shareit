package ru.practicum.shareit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements UnionService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;

    @Override
    public void checkUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(User.class, "Пользователь id " + userId + " не найден.");
        }
    }

    @Override
    public void checkItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new NotFoundException(Item.class, "Вещь id " + itemId + " не найдена.");
        }
    }

    @Override
    public void checkBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new NotFoundException(Booking.class, "Бронирование id " + bookingId + " не найдено.");
        }
    }

    @Override
    public void checkRequest(Long requestId) {

        if (!itemRequestRepository.existsById(requestId)) {
            throw new NotFoundException(ItemRequest.class, "Запрос id " + requestId + " не найден.");
        }
    }

    @Override
    public PageRequest checkPageSize(Integer from, Integer size) {

        if (from == 0 && size == 0) {
            throw new ValidationException("\"size\" and \"from\"должно быть не равно 0");
        }

        if (size <= 0) {
            throw new ValidationException("\"size\" должно быть больше 0");
        }

        if (from < 0) {
            throw new ValidationException("\"from\" должно быть больше или равно 0");
        }
        return PageRequest.of(from / size, size);
    }
}