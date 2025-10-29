package de.szut.lf8_starter.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjectGetDto {

    private Long id;
    private String name;
    private Long responsibleEmployeeId;
    private Long clientId;
    private String clientContactName;
    private String comment;
    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private LocalDate actualEndDate;
    private List<ProjectEmployeeDto> employees;

    @Getter
    @Setter
    public static class ProjectEmployeeDto {
        private Long employeeId;
        private Long roleId;
        private LocalDate startDate;
        private LocalDate endDate;
    }
}