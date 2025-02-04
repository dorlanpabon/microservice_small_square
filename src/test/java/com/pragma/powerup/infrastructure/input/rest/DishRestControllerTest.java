package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.DishRequest;
import com.pragma.powerup.application.dto.DishResponse;
import com.pragma.powerup.application.dto.DishUpdateRequest;
import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.handler.DishHandler;
import com.pragma.powerup.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DishRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class DishRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private DishHandler dishHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDishs() throws Exception {
        List<DishResponse> dishResponses = Collections.singletonList(new DishResponse());
        PaginatedResponse<DishResponse> mockResponse = new PaginatedResponse<>(dishResponses, 0, 1, 1);

        when(dishHandler.getDishs(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(mockResponse);

        mockMvc.perform(get("/dishs?page=0&size=10&sortDirection=asc&categoryId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveDishInDish() throws Exception {
        doNothing().when(dishHandler).saveDishInDish(any(DishRequest.class));

        mockMvc.perform(post("/dishs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());

        verify(dishHandler, times(1)).saveDishInDish(any(DishRequest.class));
    }

    @Test
    void testGetDishFromDish() throws Exception {
        when(dishHandler.getDishFromDish(anyLong())).thenReturn(new DishResponse());

        mockMvc.perform(get("/dishs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateDishInDish() throws Exception {
        doNothing().when(dishHandler).updateDishInDish(anyLong(), any(DishUpdateRequest.class));

        mockMvc.perform(put("/dishs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNoContent());

        verify(dishHandler, times(1)).updateDishInDish(anyLong(), any(DishUpdateRequest.class));
    }

    @Test
    void testToggleDishStatus() throws Exception {
        doNothing().when(dishHandler).toggleDishStatus(anyLong());

        mockMvc.perform(put("/dishs/1/toggle")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(dishHandler, times(1)).toggleDishStatus(anyLong());
    }
}
