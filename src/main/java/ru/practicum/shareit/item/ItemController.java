package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ErrorResponse;
import ru.practicum.shareit.exception.ValidationException;
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
    public ItemDto add(@RequestHeader(USER_ID) Long userId,
                       @RequestBody @Valid ItemDto itemDto) {
        log.info("Пользователь {}, добавил новую вещь {}", userId, itemDto.getName());
        return itemService.add(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(USER_ID) Long userId,
                          @RequestBody ItemDto itemDto,
                          @PathVariable Long itemId) {
        log.info("Пользователь {}, обновил вещь {}", userId, itemDto.getName());
        return itemService.update(itemDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@RequestHeader(USER_ID) Long userId,
                       @PathVariable Long itemId) {
        log.info("Получить вещь {}", itemId);
        return itemService.get(itemId, userId);
    }

    @GetMapping
    public List<ItemDto> getAllItemsUser(@RequestHeader(USER_ID) Long userId) {
        log.info("List вещей пользователя {}", userId);
        return itemService.getItemsUser(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getSearchItem(String text) {
        log.info("Получить вещь, содержащую {}", text);
        return itemService.searchItem(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<?> addComment(@RequestHeader(USER_ID) Long userId,
                                        @PathVariable Long itemId,
                                        @RequestBody @Valid CommentDto commentDto) {
        log.info("Пользователь {} добавил комментарий к вещи {}", userId, itemId);
        try {
            CommentDto addedComment = itemService.addComment(userId, itemId, commentDto);
            return ResponseEntity.ok(addedComment);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}