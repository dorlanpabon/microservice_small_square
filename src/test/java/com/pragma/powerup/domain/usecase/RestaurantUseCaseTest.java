package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.constants.DomainConstants;
import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserServicePort userServicePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("La Pizzeria");
        restaurant.setAddress("123 Main Street");
        restaurant.setOwnerId(100L);
        restaurant.setPhone("+573005698325");
        restaurant.setLogoUrl("http://example.com/logo.png");
        restaurant.setNit("123456789");
    }

    @Test
    void shouldSaveRestaurantSuccessfully() {
        when(userServicePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsRestaurant(restaurant.getNit())).thenReturn(false);

        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(restaurant));

        verify(userServicePort, times(1)).isOwner(restaurant.getOwnerId());
        verify(restaurantPersistencePort, times(1)).existsRestaurant(restaurant.getNit());
        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotOwner() {
        when(userServicePort.isOwner(restaurant.getOwnerId())).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(DomainConstants.USER_NOT_OWNER, exception.getMessage());
        verify(userServicePort, times(1)).isOwner(restaurant.getOwnerId());
        verify(restaurantPersistencePort, never()).existsRestaurant(anyString());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenRestaurantAlreadyExists() {
        when(userServicePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsRestaurant(restaurant.getNit())).thenReturn(true);

        DomainException exception = assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(DomainConstants.RESTAURANT_ALREADY_EXISTS, exception.getMessage());
        verify(userServicePort, times(1)).isOwner(restaurant.getOwnerId());
        verify(restaurantPersistencePort, times(1)).existsRestaurant(restaurant.getNit());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNameIsInvalid() {
        restaurant.setName("12345"); // Nombre inválido

        when(userServicePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsRestaurant(restaurant.getNit())).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(DomainConstants.NAME_INVALID_FORMAT, exception.getMessage());
        verify(userServicePort, times(1)).isOwner(restaurant.getOwnerId());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNitIsInvalid() {
        restaurant.setNit("ABC123"); // NIT inválido

        when(userServicePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsRestaurant(restaurant.getNit())).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(DomainConstants.NIT_INVALID_FORMAT, exception.getMessage());
        verify(userServicePort, times(1)).isOwner(restaurant.getOwnerId());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenPhoneIsInvalid() {
        restaurant.setPhone("ABC123"); // Teléfono inválido

        when(userServicePort.isOwner(restaurant.getOwnerId())).thenReturn(true);
        when(restaurantPersistencePort.existsRestaurant(restaurant.getNit())).thenReturn(false);

        DomainException exception = assertThrows(DomainException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(DomainConstants.PHONE_INVALID_FORMAT, exception.getMessage());
        verify(userServicePort, times(1)).isOwner(restaurant.getOwnerId());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }
}
