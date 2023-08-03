package ru.practicum.shareit.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    private final Map<Integer, List<ItemDto>> userItems = new HashMap<>();
    private int currentItemId = 1;

    @Override
    public ItemDto addItem(int userId, ItemDto itemDto) {
        itemDto.setId(currentItemId++);
        userItems.computeIfAbsent(userId, k -> new ArrayList<>()).add(itemDto);
        return itemDto;
    }

    public ItemDto editItem(int userId, int itemId, ItemDto itemDto) {
        List<ItemDto> items = userItems.get(userId);
        if (items != null) {
            for (ItemDto item : items) {
                if (item.getId() == itemId) {
                    String name = itemDto.getName();
                    String description = itemDto.getDescription();
                    Boolean available = itemDto.getAvailable();

                    if (name == null || name.isEmpty()) {
                        name = item.getName();
                    }
                    if (description == null || description.isEmpty()) {
                        description = item.getDescription();
                    }
                    if (available == null) {
                        available = item.getAvailable();
                    }

                    item.setName(name);
                    item.setDescription(description);
                    item.setAvailable(available);
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public ItemDto getItem(int itemId) {
        for (List<ItemDto> items : userItems.values()) {
            for (ItemDto item : items) {
                if (item.getId() == itemId) {
                    return item;
                }
            }
        }
        return null;
    }

    @Override
    public List<ItemDto> getItemsByUserId(int userId) {
        return userItems.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        String searchText = text.toLowerCase();
        List<ItemDto> foundItems = new ArrayList<>();
        if (searchText.isEmpty()) {
            return foundItems;
        }
        for (List<ItemDto> items : userItems.values()) {
            for (ItemDto item : items) {
                String itemName = item.getName().toLowerCase();
                String itemDescription = item.getDescription().toLowerCase();
                if (item.getAvailable() && (itemName.contains(searchText) || itemDescription.contains(searchText))) {
                    foundItems.add(item);
                }
            }
        }
        return foundItems;
    }
}
