package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.application.handler.EmployeeHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Employee API")
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeHandler employeeHandler;

    @Operation(summary = "Create a new employee", description = "Add a new employee to the system")
    @PostMapping("/")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> saveEmployeeInEmployee(@RequestBody EmployeeRequest employeeRequest) {
        employeeHandler.saveEmployeeInEmployee(employeeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
