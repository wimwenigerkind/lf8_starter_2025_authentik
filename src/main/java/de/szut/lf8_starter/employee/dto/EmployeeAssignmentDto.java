package de.szut.lf8_starter.employee.dto;

import de.szut.lf8_starter.validation.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@ValidDateRange(startDateField = "startDate", endDateField = "endDate")
public class EmployeeAssignmentDto {
    @NotNull(message = "employee id must not be null")
    private Long employeeId;

    @NotNull(message = "role must not be null")
    private String qualification;

    @NotNull(message = "start date must not be null")
    private LocalDate startDate;

    @NotNull(message = "end date must not be null")
    private LocalDate endDate;

    public @NotBlank(message = "employee role must not be blank") String getQualification() {
        return qualification;
    }
}