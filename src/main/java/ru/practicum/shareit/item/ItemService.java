package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto addItem(int userId, ItemDto itemDto);

    ItemDto editItem(int userId, int itemId, ItemDto itemDto);

    ItemDto getItem(int itemId);

    List<ItemDto> getItemsByUserId(int userId);

    List<ItemDto> searchItems(String text);
}
