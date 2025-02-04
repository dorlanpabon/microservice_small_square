package com.pragma.powerup.infrastructure.output.jpa.adapter;

import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.infrastructure.output.jpa.entity.EmployeeEntity;
import com.pragma.powerup.infrastructure.output.jpa.mapper.EmployeeEntityMapper;
import com.pragma.powerup.infrastructure.output.jpa.repository.IEmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeJpaAdapterTest {

    @Mock
    private IEmployeeRepository employeeRepository;

    @Mock
    private EmployeeEntityMapper employeeEntityMapper;

    @InjectMocks
    private EmployeeJpaAdapter employeeJpaAdapter;

    private Employee employee;
    private EmployeeEntity employeeEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setEmployeeId(101L);
        employee.setOwnerId(201L);

        employeeEntity = new EmployeeEntity();
        employeeEntity.setId(1L);
        employeeEntity.setEmployeeId(101L);
        employeeEntity.setOwnerId(201L);
    }

    @Test
    void testSaveEmployee() {
        when(employeeEntityMapper.toEntity(employee)).thenReturn(employeeEntity);
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);

        employeeJpaAdapter.saveEmployee(employee);

        verify(employeeRepository, times(1)).save(employeeEntity);
        verify(employeeEntityMapper, times(1)).toEntity(employee);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<EmployeeEntity> employeeEntities = Arrays.asList(employeeEntity);
        Page<EmployeeEntity> employeeEntityPage = new PageImpl<>(employeeEntities, pageable, employeeEntities.size());

        when(employeeRepository.findAll(pageable)).thenReturn(employeeEntityPage);
        when(employeeEntityMapper.toEmployee(any(EmployeeEntity.class))).thenReturn(employee);

        Page<Employee> result = employeeJpaAdapter.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(employee.getId(), result.getContent().get(0).getId());

        verify(employeeRepository, times(1)).findAll(pageable);
        verify(employeeEntityMapper, times(1)).toEmployee(any(EmployeeEntity.class));
    }

    @Test
    void testGetByEmployeeId() {
        Long userId = 101L;
        when(employeeRepository.findByEmployeeId(userId)).thenReturn(Optional.of(employeeEntity));
        when(employeeEntityMapper.toEmployee(employeeEntity)).thenReturn(employee);

        Employee result = employeeJpaAdapter.getByEmployeeId(userId);

        assertNotNull(result);
        assertEquals(userId, result.getEmployeeId());

        verify(employeeRepository, times(1)).findByEmployeeId(userId);
        verify(employeeEntityMapper, times(1)).toEmployee(employeeEntity);
    }

    @Test
    void testGetByEmployeeId_NotFound() {
        Long userId = 999L;
        when(employeeRepository.findByEmployeeId(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> employeeJpaAdapter.getByEmployeeId(userId));

        assertEquals("Employee not found", exception.getMessage());
        verify(employeeRepository, times(1)).findByEmployeeId(userId);
        verify(employeeEntityMapper, never()).toEmployee(any());
    }

    @Test
    void testGetEmployeeIdsByRestaurantId() {
        Long restaurantId = 1L;
        List<EmployeeEntity> employeeEntities = Arrays.asList(employeeEntity);
        when(employeeRepository.getEmployeeIdsByRestaurantId(restaurantId)).thenReturn(employeeEntities);

        List<Long> result = employeeJpaAdapter.getEmployeeIdsByRestaurantId(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeEntity.getEmployeeId(), result.get(0));

        verify(employeeRepository, times(1)).getEmployeeIdsByRestaurantId(restaurantId);
    }
}
