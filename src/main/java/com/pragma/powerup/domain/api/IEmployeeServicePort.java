package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEmployeeServicePort {

    void saveEmployee(Employee employee);

}
