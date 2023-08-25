package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import java.util.List;

public interface ItemRequestService {

    ItemRequestDto add(ItemRequestDto itemRequestDto, long userId);

    List<ItemRequestDto> get(long userId);

    List<ItemRequestDto> getAll(Long userId, Integer from, Integer size);

    ItemRequestDto getById(long userId, long requestId);

    ItemRequestDto addItemsToRequest(ItemRequest itemRequest);
}
