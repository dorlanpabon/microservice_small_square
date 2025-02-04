package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.infrastructure.output.jpa.entity.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeEntityMapperTest {

    private EmployeeEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(EmployeeEntityMapper.class);
    }

    @Test
    void testToEntity() {
        Employee employee = new Employee();
        employee.setId(10L);
        employee.setRestaurant(null);
        employee.setEmployeeId(100L);
        employee.setOwnerId(200L);

        EmployeeEntity entity = mapper.toEntity(employee);

        assertNotNull(entity, "El objeto EmployeeEntity no debe ser nulo");

        assertEquals(employee.getId(), entity.getId(), "El id no coincide");
        assertEquals(employee.getEmployeeId(), entity.getEmployeeId(), "El employeeId no coincide");
        assertEquals(employee.getOwnerId(), entity.getOwnerId(), "El ownerId no coincide");

        assertNull(entity.getRestaurant(), "El restaurante debería ser null");
    }

    @Test
    void testToEmployee() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(20L);
        entity.setRestaurant(null);
        entity.setEmployeeId(300L);
        entity.setOwnerId(400L);

        Employee employee = mapper.toEmployee(entity);

        assertNotNull(employee, "El objeto Employee no debe ser nulo");
        assertEquals(entity.getId(), employee.getId(), "El id no coincide");
        assertEquals(entity.getEmployeeId(), employee.getEmployeeId(), "El employeeId no coincide");
        assertEquals(entity.getOwnerId(), employee.getOwnerId(), "El ownerId no coincide");

        assertNull(employee.getRestaurant(), "El restaurante debería ser null");
    }
}
