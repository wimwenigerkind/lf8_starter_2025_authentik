package de.szut.lf8_starter.project.dto;

import de.szut.lf8_starter.validation.ValidDateRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ValidDateRange(startDateField = "startDate", endDateField = "plannedEndDate")
@ValidDateRange(startDateField = "startDate", endDateField = "actualEndDate")
public class ProjectUpdateDto {
    @NotBlank(message = "project name must not be blank")
    private String name;

    @NotNull(message = "responsible employee id must not be null")
    private Long responsibleEmployeeId;

    @NotNull(message = "client id must not be null")
    private Long clientId;

    @NotBlank(message = "client contact name must not be blank")
    private String clientContactName;

    private String comment;

    @NotNull(message = "start date must not be null")
    private LocalDate startDate;

    @NotNull(message = "planned end date must not be null")
    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;

    private List<Long> qualificationIds;
}