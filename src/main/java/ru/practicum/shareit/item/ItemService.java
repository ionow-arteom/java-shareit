package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto add(int userId, ItemDto itemDto);

    ItemDto edit(int userId, int itemId, ItemDto itemDto);

    ItemDto get(int itemId);

    List<ItemDto> getItemsByUserId(int userId);

    List<ItemDto> searchItems(String text);
}
