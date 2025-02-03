package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.application.mapper.EmployeeRequestMapper;
import com.pragma.powerup.application.mapper.EmployeeResponseMapper;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeHandler implements IEmployeeHandler {

    private final EmployeeRequestMapper employeeRequestMapper;
    private final EmployeeResponseMapper employeeResponseMapper;
    private final IEmployeeServicePort employeeServicePort;

    @Override
    public void saveEmployeeInEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeRequestMapper.toEmployee(employeeRequest);
        employeeServicePort.saveEmployee(employee);
    }

}
