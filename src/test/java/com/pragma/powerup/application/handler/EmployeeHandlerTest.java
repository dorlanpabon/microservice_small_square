package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.application.mapper.EmployeeRequestMapper;
import com.pragma.powerup.application.mapper.EmployeeResponseMapper;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EmployeeHandlerTest {
    @Mock
    EmployeeRequestMapper employeeRequestMapper;
    @Mock
    EmployeeResponseMapper employeeResponseMapper;
    @Mock
    IEmployeeServicePort employeeServicePort;
    @InjectMocks
    EmployeeHandler employeeHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEmployeeInEmployee() {
        when(employeeRequestMapper.toEmployee(any(EmployeeRequest.class))).thenReturn(new Employee());

        employeeHandler.saveEmployeeInEmployee(new EmployeeRequest());
        verify(employeeServicePort).saveEmployee(any(Employee.class));
    }
}