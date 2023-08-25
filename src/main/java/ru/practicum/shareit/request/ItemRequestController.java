package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import javax.validation.Valid;
import java.util.List;
import static ru.practicum.shareit.service.Constant.USER_ID;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ResponseEntity<ItemRequestDto> add(@RequestHeader(USER_ID) Long userId,
                                              @RequestBody @Valid ItemRequestDto itemRequestDto) {
        log.info("Пользователь {}, добавил новый запрос", userId);
        return ResponseEntity.ok(itemRequestService.add(itemRequestDto, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> get(@RequestHeader(USER_ID) Long userId) {
        log.info("Получить запросы пользователя с Id {}", userId);
        return ResponseEntity.ok(itemRequestService.get(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> getAll(@RequestHeader(USER_ID) Long userId,
                                                       @RequestParam(defaultValue = "0", required = false) Integer from,
                                                       @RequestParam(defaultValue = "10",required = false) Integer size) {
        log.info("Получить все запросы от всех пользователей ");
        return ResponseEntity.ok(itemRequestService.getAll(userId, from, size));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> getRequestById(@RequestHeader(USER_ID) Long userId,
                                                         @PathVariable("requestId") Long requestId) {
        log.info("Получить запрос {}", requestId);
        return ResponseEntity.ok(itemRequestService.getById(userId, requestId));
    }
}
