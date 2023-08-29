package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRequestMapperTest {

    @Test
    void returnItemRequestDto() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        ItemRequest itemRequest = ItemRequest.builder()
                .id(123L)
                .description("Urgent request")
                .created(LocalDateTime.now())
                .requester(user)
                .build();

        ItemRequestDto itemRequestDto = ItemRequestMapper.returnItemRequestDto(itemRequest);

        assertThat(itemRequestDto.getId()).isEqualTo(123L);
        assertThat(itemRequestDto.getDescription()).isEqualTo("Urgent request");
        assertThat(itemRequestDto.getCreated()).isNotNull();
        assertThat(itemRequestDto.getItems()).isEmpty();
    }

    @Test
    void returnItemRequest() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .description("Urgent request")
                .build();

        ItemRequest itemRequest = ItemRequestMapper.returnItemRequest(itemRequestDto, user);

        assertThat(itemRequest.getDescription()).isEqualTo("Urgent request");
        assertThat(itemRequest.getCreated()).isNotNull();
        assertThat(itemRequest.getRequester()).isEqualTo(user);
    }

    @Test
    void returnItemRequestDtoList() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        ItemRequest itemRequest1 = ItemRequest.builder()
                .id(123L)
                .description("Urgent request 1")
                .created(LocalDateTime.now())
                .requester(user)
                .build();

        ItemRequest itemRequest2 = ItemRequest.builder()
                .id(456L)
                .description("Urgent request 2")
                .created(LocalDateTime.now())
                .requester(user)
                .build();

        List<ItemRequest> itemRequestList = List.of(itemRequest1, itemRequest2);

        List<ItemRequestDto> itemRequestDtoList = ItemRequestMapper.returnItemRequestDtoList(itemRequestList);

        assertThat(itemRequestDtoList).hasSize(2);
        assertThat(itemRequestDtoList.get(0).getId()).isEqualTo(123L);
        assertThat(itemRequestDtoList.get(0).getDescription()).isEqualTo("Urgent request 1");
        assertThat(itemRequestDtoList.get(0).getCreated()).isNotNull();
        assertThat(itemRequestDtoList.get(0).getItems()).isEmpty();
        assertThat(itemRequestDtoList.get(1).getId()).isEqualTo(456L);
        assertThat(itemRequestDtoList.get(1).getDescription()).isEqualTo("Urgent request 2");
        assertThat(itemRequestDtoList.get(1).getCreated()).isNotNull();
        assertThat(itemRequestDtoList.get(1).getItems()).isEmpty();
    }
}

