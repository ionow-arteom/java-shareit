package ru.practicum.shareit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UnionServiceImpl implements UnionService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

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
}