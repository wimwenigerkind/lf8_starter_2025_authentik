package de.szut.lf8_starter.employee.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EmployeeGetDto {

    private Long id;
    private String name;
    private String role;
}
