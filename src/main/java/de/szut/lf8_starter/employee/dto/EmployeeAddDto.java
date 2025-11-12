package de.szut.lf8_starter.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeAddDto {

    @NotNull(message = "employee id must not be null")
    private Long employeeId;

    @NotBlank(message = "employee role must not be blank")
    private String qualification;

    @NotNull(message = "role id must not be null")
    private Long roleId;
}