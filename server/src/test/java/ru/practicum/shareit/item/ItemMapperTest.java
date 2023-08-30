package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemMapperTest {

    @Test
    void returnItemDto() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        Item item = Item.builder()
                .id(123L)
                .name("Laptop")
                .description("A powerful laptop")
                .available(true)
                .owner(user)
                .build();

        ItemDto itemDto = ItemMapper.returnItemDto(item);

        assertThat(itemDto.getId()).isEqualTo(123L);
        assertThat(itemDto.getName()).isEqualTo("Laptop");
        assertThat(itemDto.getDescription()).isEqualTo("A powerful laptop");
        assertThat(itemDto.getAvailable()).isEqualTo(true);
        assertThat(itemDto.getRequestId()).isNull();
    }

    @Test
    void returnItem() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        ItemDto itemDto = ItemDto.builder()
                .id(123L)
                .name("Laptop")
                .description("A powerful laptop")
                .available(true)
                .build();

        Item item = ItemMapper.returnItem(itemDto, user);

        assertThat(item.getId()).isEqualTo(123L);
        assertThat(item.getName()).isEqualTo("Laptop");
        assertThat(item.getDescription()).isEqualTo("A powerful laptop");
        assertThat(item.getAvailable()).isEqualTo(true);
        assertThat(item.getOwner()).isEqualTo(user);
    }

    @Test
    void returnItemDtoList() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();

        Item item1 = Item.builder()
                .id(123L)
                .name("Laptop")
                .description("A powerful laptop")
                .available(true)
                .owner(user)
                .build();

        Item item2 = Item.builder()
                .id(456L)
                .name("Phone")
                .description("A high-end smartphone")
                .available(true)
                .owner(user)
                .build();

        List<Item> itemList = List.of(item1, item2);

        List<ItemDto> itemDtoList = ItemMapper.returnItemDtoList(itemList);

        assertThat(itemDtoList).hasSize(2);
        assertThat(itemDtoList.get(0).getId()).isEqualTo(123L);
        assertThat(itemDtoList.get(0).getName()).isEqualTo("Laptop");
        assertThat(itemDtoList.get(0).getDescription()).isEqualTo("A powerful laptop");
        assertThat(itemDtoList.get(0).getAvailable()).isEqualTo(true);
        assertThat(itemDtoList.get(0).getRequestId()).isNull();
        assertThat(itemDtoList.get(1).getId()).isEqualTo(456L);
        assertThat(itemDtoList.get(1).getName()).isEqualTo("Phone");
        assertThat(itemDtoList.get(1).getDescription()).isEqualTo("A high-end smartphone");
        assertThat(itemDtoList.get(1).getAvailable()).isEqualTo(true);
        assertThat(itemDtoList.get(1).getRequestId()).isNull();
    }
}