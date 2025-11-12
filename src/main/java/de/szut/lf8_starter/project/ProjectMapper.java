package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectEmployeesDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import de.szut.lf8_starter.project.dto.ProjectUpdateDto;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {
    public ProjectEntity mapCreateDtoToEntity(ProjectCreateDto dto) {
        ProjectEntity entity = new ProjectEntity();
        entity.setName(dto.getName());
        entity.setResponsibleEmployeeId(dto.getResponsibleEmployeeId());
        entity.setClientId(dto.getClientId());
        entity.setClientContactName(dto.getClientContactName());
        entity.setComment(dto.getComment());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        entity.setActualEndDate(dto.getActualEndDate());
        entity.setQualificationIds(dto.getQualificationIds());

        return entity;
    }

    public ProjectGetDto mapEntityToGetDto(ProjectEntity entity) {
        ProjectGetDto dto = new ProjectGetDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setResponsibleEmployeeId(entity.getResponsibleEmployeeId());
        dto.setClientId(entity.getClientId());
        dto.setClientContactName(entity.getClientContactName());
        dto.setComment(entity.getComment());
        dto.setStartDate(entity.getStartDate());
        dto.setPlannedEndDate(entity.getPlannedEndDate());
        dto.setActualEndDate(entity.getActualEndDate());
        dto.setQualificationIds(entity.getQualificationIds());

        if (entity.getEmployees() != null) {
            dto.setEmployees(entity.getEmployees().stream()
                    .map(this::mapEmployeeEntityToDto)
                    .toList());
        }

        return dto;
    }

    private ProjectGetDto.ProjectEmployeeDto mapEmployeeEntityToDto(EmployeeEntity entity) {
        ProjectGetDto.ProjectEmployeeDto dto = new ProjectGetDto.ProjectEmployeeDto();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setRoleId(entity.getRoleId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        return dto;
    }

    public ProjectEmployeesDto mapEntityToEmployeesDto(ProjectEntity entity) {
        ProjectEmployeesDto dto = new ProjectEmployeesDto();
        dto.setProjectId(entity.getId());
        dto.setProjectName(entity.getName());

        if (entity.getEmployees() != null) {
            dto.setEmployees(entity.getEmployees().stream()
                    .map(this::mapEmployeeEntityToEmployeeRoleDto)
                    .toList());
        }

        return dto;
    }

    private ProjectEmployeesDto.EmployeeRoleDto mapEmployeeEntityToEmployeeRoleDto(EmployeeEntity entity) {
        ProjectEmployeesDto.EmployeeRoleDto dto = new ProjectEmployeesDto.EmployeeRoleDto();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setRoleId(entity.getRoleId());
        return dto;
    }

    public void updateEntityFromDto(ProjectUpdateDto dto, ProjectEntity entity) {
        entity.setName(dto.getName());
        entity.setResponsibleEmployeeId(dto.getResponsibleEmployeeId());
        entity.setClientId(dto.getClientId());
        entity.setClientContactName(dto.getClientContactName());
        entity.setComment(dto.getComment());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedEndDate(dto.getPlannedEndDate());
        entity.setActualEndDate(dto.getActualEndDate());
        entity.setQualificationIds(dto.getQualificationIds());
    }
}