package de.szut.lf8_starter.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectCreateDto {
    private String name;
    private Long responsibleEmployeeId;
    private Long clientId;
    private String clientContactName;
    private String comment;
    private LocalDate startDate;
    private LocalDate plannedEndDate;
    private LocalDate actualEndDate;
}