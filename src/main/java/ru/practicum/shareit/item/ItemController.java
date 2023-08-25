package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.validation.Valid;
import java.util.List;

import static ru.practicum.shareit.service.Constant.USER_ID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> add(@RequestHeader(USER_ID) Long userId,
                                       @RequestBody @Valid ItemDto itemDto) {
        log.info("Пользователь {}, добавил новую вещь {}", userId, itemDto.getName());
        return ResponseEntity.ok(itemService.add(userId, itemDto));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> update(@RequestHeader(USER_ID) Long userId,
                                          @RequestBody ItemDto itemDto,
                                          @PathVariable Long itemId) {
        log.info("Пользователь {}, обновил вещь {}", userId, itemDto.getName());
        return ResponseEntity.ok(itemService.update(itemDto, itemId, userId));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> get(@RequestHeader(USER_ID) Long userId,
                                       @PathVariable Long itemId) {
        log.info("Получить вещь {}", itemId);
        return ResponseEntity.ok(itemService.get(itemId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItemsUser(@RequestHeader(USER_ID) Long userId,
                                                         @RequestParam(required = false, defaultValue = "0") Integer from,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("List вещей пользователя {}", userId);
        return ResponseEntity.ok(itemService.getItemsUser(userId, from, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> getSearchItem(@RequestParam String text,
                                                       @RequestParam(required = false, defaultValue = "0") Integer from,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Получить вещь, содержащую {}", text);
        return ResponseEntity.ok(itemService.searchItem(text, from, size));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> addComment(@RequestHeader(USER_ID) Long userId,
                                        @PathVariable Long itemId,
                                        @RequestBody @Valid CommentDto commentDto) {
        log.info("Пользователь {} добавил комментарий к вещи {}", userId, itemId);
        return ResponseEntity.ok(itemService.addComment(userId, itemId, commentDto));
    }
}