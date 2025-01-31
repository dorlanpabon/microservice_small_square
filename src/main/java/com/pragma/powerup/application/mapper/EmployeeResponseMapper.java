package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.EmployeeResponse;
import com.pragma.powerup.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EmployeeResponseMapper {
    @Mapping(target = "employeeId", source = "id")
    @Mapping(target = "employeeRestaurant", source = "restaurant")
    @Mapping(target = "employeeEmployeeId", source = "employeeId")
    @Mapping(target = "employeeOwnerId", source = "ownerId")
    EmployeeResponse toEmployeeResponse(Employee entity);
}
