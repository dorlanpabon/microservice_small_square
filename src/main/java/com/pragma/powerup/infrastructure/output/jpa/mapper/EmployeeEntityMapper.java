package com.pragma.powerup.infrastructure.output.jpa.mapper;

import com.pragma.powerup.domain.model.Employee;
import com.pragma.powerup.infrastructure.output.jpa.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EmployeeEntityMapper {

    EmployeeEntity toEntity(Employee employee);

    Employee toEmployee(EmployeeEntity employeeEntity);

}
