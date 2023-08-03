package ru.practicum.shareit.item;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@Validated
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestHeader("X-Sharer-User-Id") int userId, @Valid @RequestBody ItemDto itemDto) {
        UserDto user = userService.getUser(userId);
        String name = itemDto.getName();
        String description = itemDto.getDescription();
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (itemDto.getAvailable() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ItemDto newItem = itemService.addItem(userId, itemDto);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> editItem(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int itemId, @Valid @RequestBody ItemDto itemDto) {
        ItemDto editedItem = itemService.editItem(userId, itemId, itemDto);
        if (editedItem != null) {
            return new ResponseEntity<>(editedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable int itemId) {
        ItemDto item = itemService.getItem(itemId);
        if (item != null) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemsByUser(@RequestHeader("X-Sharer-User-Id") int userId) {
        List<ItemDto> items = itemService.getItemsByUserId(userId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam("text") String text) {
        String searchText = text.toLowerCase();
        List<ItemDto> foundItems = itemService.searchItems(searchText);
        return new ResponseEntity<>(foundItems, HttpStatus.OK);
    }
}
