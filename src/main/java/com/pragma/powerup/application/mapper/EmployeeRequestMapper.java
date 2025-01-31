package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.EmployeeRequest;
import com.pragma.powerup.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EmployeeRequestMapper {
    @Mapping(target = "employeeId", source = "employeeId")
    @Mapping(target = "ownerId", source = "ownerId")
    Employee toEmployee(EmployeeRequest employeeRequest);
}
