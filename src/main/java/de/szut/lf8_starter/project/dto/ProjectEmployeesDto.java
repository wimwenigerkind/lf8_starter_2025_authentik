package de.szut.lf8_starter.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectEmployeesDto {
    private Long projectId;
    private String projectName;
    private List<EmployeeRoleDto> employees;

    @Getter
    @Setter
    public static class EmployeeRoleDto {
        private Long employeeId;
        private Long roleId;
    }
}