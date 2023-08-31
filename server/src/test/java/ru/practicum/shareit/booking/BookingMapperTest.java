package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookingMapperTest {

    @Test
    void returnBookingDto() {
        Item item = Item.builder()
                .id(123L)
                .name("Book")
                .description("A great book")
                .available(true)
                .build();

        User booker = User.builder()
                .id(456L)
                .name("Alice")
                .email("alice@example.com")
                .build();

        Booking booking = Booking.builder()
                .id(789L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(7))
                .status(Status.APPROVED)
                .item(item)
                .booker(booker)
                .build();

        BookingOutDto bookingOutDto = BookingMapper.returnBookingDto(booking);

        assertThat(bookingOutDto.getId()).isEqualTo(789L);
        assertThat(bookingOutDto.getStart()).isNotNull();
        assertThat(bookingOutDto.getEnd()).isNotNull();
        assertThat(bookingOutDto.getStatus()).isEqualTo(Status.APPROVED);
        assertThat(bookingOutDto.getItem()).isNotNull();
        assertThat(bookingOutDto.getBooker()).isNotNull();
    }

    @Test
    void returnBookingShortDto() {
        User booker = User.builder()
                .id(456L)
                .name("Alice")
                .email("alice@example.com")
                .build();

        Booking booking = Booking.builder()
                .id(789L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(7))
                .booker(booker)
                .build();

        BookingShortDto bookingShortDto = BookingMapper.returnBookingShortDto(booking);

        assertThat(bookingShortDto.getId()).isEqualTo(789L);
        assertThat(bookingShortDto.getStart()).isNotNull();
        assertThat(bookingShortDto.getEnd()).isNotNull();
        assertThat(bookingShortDto.getBookerId()).isEqualTo(456L);
    }

    @Test
    void returnBooking() {
        BookingDto bookingDto = BookingDto.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(7))
                .status(Status.APPROVED)
                .build();

        Booking booking = BookingMapper.returnBooking(bookingDto);

        assertThat(booking.getStart()).isNotNull();
        assertThat(booking.getEnd()).isNotNull();
        assertThat(booking.getStatus()).isEqualTo(Status.APPROVED);
    }

    @Test
    void returnBookingDtoList() {
        Item item = Item.builder()
                .id(123L)
                .name("Book")
                .description("A great book")
                .available(true)
                .build();

        User booker = User.builder()
                .id(456L)
                .name("Alice")
                .email("alice@example.com")
                .build();

        Booking booking1 = Booking.builder()
                .id(789L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(7))
                .status(Status.APPROVED)
                .item(item)
                .booker(booker)
                .build();

        Booking booking2 = Booking.builder()
                .id(987L)
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(14))
                .status(Status.WAITING)
                .item(item)
                .booker(booker)
                .build();

        List<Booking> bookingList = List.of(booking1, booking2);

        List<BookingOutDto> bookingDtoList = BookingMapper.returnBookingDtoList(bookingList);

        assertThat(bookingDtoList).hasSize(2);
        assertThat(bookingDtoList.get(0)).isNotNull();
        assertThat(bookingDtoList.get(1)).isNotNull();
    }
}