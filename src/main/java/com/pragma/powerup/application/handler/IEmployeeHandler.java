package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.application.dto.EmployeeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeHandler {

    Page<EmployeeResponse> getEmployees(int page, int size, String sortDirection);

    void saveEmployeeInEmployee(EmployeeRequest employeeRequest);

    List<EmployeeResponse> getEmployeeFromEmployee();

    EmployeeResponse getEmployeeFromEmployee(Long employeeId);

    void updateEmployeeInEmployee(EmployeeRequest employeeRequest);

    void deleteEmployeeFromEmployee(Long employeeId);

}
