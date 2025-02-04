package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.PaginatedResponse;
import com.pragma.powerup.application.dto.RestaurantRequest;
import com.pragma.powerup.application.dto.RestaurantResponse;
import com.pragma.powerup.application.handler.IRestaurantHandler;
import com.pragma.powerup.infrastructure.security.JwtFilter;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class RestaurantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private IRestaurantHandler restaurantHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRestaurants() throws Exception {
        List<RestaurantResponse> restaurants = Collections.singletonList(new RestaurantResponse());
        PaginatedResponse<RestaurantResponse> mockResponse = new PaginatedResponse<>(restaurants, 0, 1, 1);

        when(restaurantHandler.getRestaurants(anyInt(), anyInt(), anyString())).thenReturn(mockResponse);

        mockMvc.perform(get("/restaurants")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        verify(restaurantHandler, times(1)).getRestaurants(anyInt(), anyInt(), anyString());
    }

    @Test
    void testSaveRestaurantInRestaurant() throws Exception {
        doNothing().when(restaurantHandler).saveRestaurantInRestaurant(any(RestaurantRequest.class));

        mockMvc.perform(post("/restaurants/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Restaurant\",\"address\":\"123 Street\",\"phone\":\"123456789\",\"ownerId\":1}"))
                .andExpect(status().isCreated());

        verify(restaurantHandler, times(1)).saveRestaurantInRestaurant(any(RestaurantRequest.class));
    }
}
