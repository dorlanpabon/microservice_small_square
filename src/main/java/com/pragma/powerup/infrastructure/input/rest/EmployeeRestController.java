package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.application.dto.EmployeeResponse;
import com.pragma.powerup.application.handler.EmployeeHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Employee API")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeHandler employeeHandler;

    @Operation(summary = "Get all employees", description = "Retrieve a list of employees")
    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        Page<EmployeeResponse> employees = employeeHandler.getEmployees(page, size, sortDirection);
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Create a new employee", description = "Add a new employee to the system")
    @PostMapping("/")
    public ResponseEntity<Void> saveEmployeeInEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeHandler.saveEmployeeInEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Retrieve Employee list", description = "Get all employees")
    @GetMapping("/")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeFromEmployee() {
        return ResponseEntity.ok(employeeHandler.getEmployeeFromEmployee());
    }

    @Operation(summary = "Retrieve Employee by ID", description = "Get a single employee by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeFromEmployee(@PathVariable(name = "id") Long employeeId) {
        return ResponseEntity.ok(employeeHandler.getEmployeeFromEmployee(employeeId));
    }

    @Operation(summary = "Update employee", description = "Update an existing employee in the system")
    @PutMapping("/")
    public ResponseEntity<Void> updateEmployeeInEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeHandler.updateEmployeeInEmployee(employeeRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete employee by ID", description = "Remove an existing employee from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeFromEmployee(@PathVariable Long employeeId) {
        employeeHandler.deleteEmployeeFromEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

}
