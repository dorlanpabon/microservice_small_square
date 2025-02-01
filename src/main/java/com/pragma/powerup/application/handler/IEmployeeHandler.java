package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.application.dto.EmployeeResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeHandler {

    void saveEmployeeInEmployee(EmployeeRequest employeeRequest);

}
