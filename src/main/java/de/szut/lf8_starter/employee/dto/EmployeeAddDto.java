package de.szut.lf8_starter.employee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeAddDto {

    @NotNull(message = "employee id must not be null")
    private Long employeeId;

    @NotNull(message = "role id must not be null")
    private Long roleId;
}