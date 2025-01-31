package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.application.dto.EmployeeResponse;
import com.pragma.powerup.application.mapper.EmployeeRequestMapper;
import com.pragma.powerup.application.mapper.EmployeeResponseMapper;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeHandler implements IEmployeeHandler {

    private final EmployeeRequestMapper employeeRequestMapper;
    private final EmployeeResponseMapper employeeResponseMapper;
    private final IEmployeeServicePort employeeServicePort;

    @Override
    public Page<EmployeeResponse> getEmployees(int page, int size, String sortDirection) {
        return employeeServicePort.getEmployees(page, size, sortDirection)
                .map(employeeResponseMapper::toEmployeeResponse);
    }

    @Override
    public void saveEmployeeInEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeRequestMapper.toEmployee(employeeRequest);
        employeeServicePort.saveEmployee(employee);
    }

    @Override
    public List<EmployeeResponse> getEmployeeFromEmployee() {
        return employeeServicePort.getAllEmployee().stream()
                .map(employeeResponseMapper::toEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployeeFromEmployee(Long employeeId) {
        Employee employee = employeeServicePort.getEmployeeById(employeeId);
        return employeeResponseMapper.toEmployeeResponse(employee);
    }

    @Override
    public void updateEmployeeInEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeRequestMapper.toEmployee(employeeRequest);
        employeeServicePort.updateEmployee(employee);
    }

    @Override
    public void deleteEmployeeFromEmployee(Long employeeId) {
        employeeServicePort.deleteEmployee(employeeId);
    }

}
