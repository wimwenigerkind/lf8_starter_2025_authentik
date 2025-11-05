package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeEntity;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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

        if (entity.getEmployees() != null) {
            dto.setEmployees(entity.getEmployees().stream()
                    .map(this::mapEmployeeEntityToDto)
                    .collect(Collectors.toList()));
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
}