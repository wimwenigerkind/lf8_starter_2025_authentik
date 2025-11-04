package de.szut.lf8_starter.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

    @Getter
    @Setter
    public class EmployeeAddDto {
        @NotBlank(message = "employee name must not be blank")
        private String name;

        @NotNull(message = "responsible employee id must not be null")
        private Long responsibleEmployeeId;

        @NotBlank(message = "employee role must not be blank")
        private String role;

    }