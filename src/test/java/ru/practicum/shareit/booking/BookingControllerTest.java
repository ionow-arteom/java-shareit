package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.practicum.shareit.service.Constant.USER_ID;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private ItemDto itemDto;

    private UserDto user;

    private BookingDto bookingDto;

    private BookingOutDto firstBookingOutDto;

    private BookingOutDto secondBookingOutDto;


    @BeforeEach
    void beforeEach() {

        user = UserDto.builder()
                .id(1L)
                .name("Puka")
                .email("Puka@akup.ru")
                .build();

        itemDto = ItemDto.builder()
                .requestId(1L)
                .name("screwdriver")
                .description("works well")
                .available(true)
                .build();

        bookingDto = BookingDto.builder()
                .itemId(1L)
                .start(LocalDateTime.of(2023, 9, 4, 0, 0))
                .end(LocalDateTime.of(2023, 9, 4, 12, 0))
                .build();

        firstBookingOutDto = BookingOutDto.builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 9, 4, 0, 0))
                .end(LocalDateTime.of(2023, 9, 4, 12, 0))
                .item(itemDto)
                .booker(user)
                .status(Status.APPROVED)
                .build();

        secondBookingOutDto = BookingOutDto.builder()
                .id(2L)
                .start(LocalDateTime.of(2023, 9, 4, 14, 0))
                .end(LocalDateTime.of(2023, 9, 4, 16, 0))
                .item(itemDto)
                .booker(user)
                .status(Status.APPROVED)
                .build();
    }

    @Test
    void add() throws Exception {
        when(bookingService.add(any(BookingDto.class), anyLong())).thenReturn(firstBookingOutDto);

        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstBookingOutDto.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(firstBookingOutDto.getStatus().toString()), Status.class))
                .andExpect(jsonPath("$.booker.id", is(firstBookingOutDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$.item.id", is(firstBookingOutDto.getItem().getId()), Long.class));

        verify(bookingService, times(1)).add(bookingDto, 1L);
    }

    @Test
    void approveBooking() throws Exception {
        when(bookingService.approve(anyLong(),anyLong(), anyBoolean())).thenReturn(firstBookingOutDto);

        mvc.perform(patch("/bookings/{bookingId}", 1L)
                        .param("approved", "true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstBookingOutDto.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(firstBookingOutDto.getStatus().toString()), Status.class))
                .andExpect(jsonPath("$.booker.id", is(firstBookingOutDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$.item.id", is(firstBookingOutDto.getItem().getId()), Long.class));

        verify(bookingService, times(1)).approve(1L, 1L, true);
    }

    @Test
    void getBookingById() throws Exception {
        when(bookingService.getById(anyLong(),anyLong())).thenReturn(firstBookingOutDto);

        mvc.perform(get("/bookings/{bookingId}", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstBookingOutDto.getId()), Long.class))
                .andExpect(jsonPath("$.status", is(firstBookingOutDto.getStatus().toString()), Status.class))
                .andExpect(jsonPath("$.booker.id", is(firstBookingOutDto.getBooker().getId()), Long.class))
                .andExpect(jsonPath("$.item.id", is(firstBookingOutDto.getItem().getId()), Long.class));

        verify(bookingService, times(1)).getById(1L, 1L);
    }

    @Test
    void getAllBookingsByBookerId() throws Exception {
        when(bookingService.getAllBookingsByBookerId(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(List.of(firstBookingOutDto, secondBookingOutDto));

        mvc.perform(get("/bookings")
                        .param("state", "ALL")
                        .param("from", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(firstBookingOutDto, secondBookingOutDto))));

        verify(bookingService, times(1)).getAllBookingsByBookerId(1L, "ALL", 0, 10);
    }

    @Test
    void getAllBookingsForAllItemsByOwnerId() throws Exception {
        when(bookingService.getAllBookingsForAllItemsByOwnerId(anyLong(), anyString(), anyInt(), anyInt())).thenReturn(List.of(firstBookingOutDto, secondBookingOutDto));

        mvc.perform(get("/bookings/owner")
                        .param("state", "ALL")
                        .param("from", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(firstBookingOutDto, secondBookingOutDto))));

        verify(bookingService, times(1)).getAllBookingsForAllItemsByOwnerId(1L, "ALL", 0, 10);
    }
}
