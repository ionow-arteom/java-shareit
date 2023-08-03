package ru.practicum.shareit.item.model;

/**
 * TODO Sprint add-controllers.
 */
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;

import javax.validation.constraints.NotBlank;

@Data
public class Item {
    private int id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @NotBlank(message = "Описание не может быть пустым")
    private String description;
    private Boolean available;
    private String owner;
    private ItemRequest request;
}
