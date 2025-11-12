package de.szut.lf8_starter.employee.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EmployeeGetDto {

    private Long id;
    private Long employeeId;
    private Long roleId;
    private LocalDate startDate;
    private LocalDate endDate;
}
