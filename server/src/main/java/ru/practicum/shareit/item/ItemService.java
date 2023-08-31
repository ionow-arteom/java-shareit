package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto add(long owner, ItemDto itemDto);

    ItemDto update(ItemDto itemDto, long itemId, long userId);

    ItemDto get(long itemId, long userId);

    List<ItemDto> getItemsUser(long userId, Integer from, Integer size);

    List<ItemDto> searchItem(String text, Integer from, Integer size);

    CommentDto addComment(long userId, long itemId, CommentDto commentDto);
}