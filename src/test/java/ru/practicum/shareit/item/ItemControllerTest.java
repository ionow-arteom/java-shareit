package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.practicum.shareit.service.Constant.USER_ID;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    private ItemDto firstitemDto;

    private ItemDto secondItemDto;
    private ItemDto thirditemDto;

    private CommentDto commentDto;

    private ItemRequest itemRequest;

    private User user;

    @BeforeEach
    void beforeEach() {

        user = User.builder()
                .id(1L)
                .name("Puka")
                .email("Puka@akup.ru")
                .build();

        itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Puka")
                .requester(user)
                .created(LocalDateTime.now())
                .build();

        commentDto = CommentDto.builder()
                .id(1L)
                .text("acceptable")
                .created(LocalDateTime.now())
                .authorName("ALex")
                .build();

        firstitemDto = ItemDto.builder()
                .id(1L)
                .name("screwdriver")
                .description("works well")
                .available(true)
                .comments(List.of(commentDto))
                .requestId(itemRequest.getId())
                .build();

        secondItemDto = ItemDto.builder()
                .id(1L)
                .name("guitar")
                .description("a very good tool")
                .available(true)
                .comments(Collections.emptyList())
                .requestId(itemRequest.getId())
                .build();
    }

    @Test
    void addItem() throws Exception {
        when(itemService.add(anyLong(), any(ItemDto.class))).thenReturn(firstitemDto);

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(firstitemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstitemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(firstitemDto.getName()), String.class))
                .andExpect(jsonPath("$.description", is(firstitemDto.getDescription()), String.class))
                .andExpect(jsonPath("$.available", is(firstitemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$.requestId", is(firstitemDto.getRequestId()), Long.class));

        verify(itemService, times(1)).add(1L, firstitemDto);
    }

    @Test
    void updateItem() throws Exception {
        when(itemService.update(any(ItemDto.class), anyLong(), anyLong())).thenReturn(firstitemDto);

        mvc.perform(patch("/items/{itemId}", 1L)
                        .content(mapper.writeValueAsString(firstitemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstitemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(firstitemDto.getName()), String.class))
                .andExpect(jsonPath("$.description", is(firstitemDto.getDescription()), String.class))
                .andExpect(jsonPath("$.available", is(firstitemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$.requestId", is(firstitemDto.getRequestId()), Long.class));

        verify(itemService, times(1)).update(firstitemDto, 1L, 1L);
    }

    @Test
    void getItemById() throws Exception {
        when(itemService.get(anyLong(), anyLong())).thenReturn(firstitemDto);

        mvc.perform(get("/items/{itemId}", 1L)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(firstitemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(firstitemDto.getName()), String.class))
                .andExpect(jsonPath("$.description", is(firstitemDto.getDescription()), String.class))
                .andExpect(jsonPath("$.available", is(firstitemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$.requestId", is(firstitemDto.getRequestId()), Long.class));

        verify(itemService, times(1)).get(1L, 1L);
    }

    @Test
    void getAllItemsUser() throws Exception {

        when(itemService.getItemsUser(anyLong(), anyInt(), anyInt())).thenReturn(List.of(firstitemDto, secondItemDto));

        mvc.perform(get("/items")
                        .param("from", "0")
                        .param("size", "10")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(firstitemDto, secondItemDto))));

        verify(itemService, times(1)).getItemsUser(1L, 0, 10);
    }

    @Test
    void getSearchItem() throws Exception {
        when(itemService.searchItem(anyString(), anyInt(), anyInt())).thenReturn(List.of(firstitemDto, secondItemDto));

        mvc.perform(get("/items/search")
                        .param("text", "text")
                        .param("from", "0")
                        .param("size", "10")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(firstitemDto, secondItemDto))));

        verify(itemService, times(1)).searchItem("text", 0, 10);
    }

    @Test
    void addComment() throws Exception {
        when(itemService.addComment(anyLong(), anyLong(), any(CommentDto.class))).thenReturn(commentDto);

        mvc.perform(post("/items/{itemId}/comment", 1L)
                        .content(mapper.writeValueAsString(commentDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(commentDto)));

        verify(itemService, times(1)).addComment(1L, 1L, commentDto);
    }

    @Test
    void invalidItem_AddItem_ReturnsBadRequest() throws Exception {
        thirditemDto = ItemDto.builder().build();

        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(thirditemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void edgeCasePagination_ReturnsCorrectResults() throws Exception {
        int totalItems = 15;
        when(itemService.getItemsUser(anyLong(), anyInt(), anyInt()))
                .thenReturn(Collections.nCopies(totalItems, firstitemDto));

        mvc.perform(get("/items")
                        .param("from", "0")
                        .param("size", String.valueOf(totalItems))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(totalItems)));
    }

    @Test
    void searchItem_NoMatch_ReturnsEmptyList() throws Exception {
        when(itemService.searchItem(anyString(), anyInt(), anyInt())).thenReturn(Collections.emptyList());

        mvc.perform(get("/items/search")
                        .param("text", "nonexistent")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}