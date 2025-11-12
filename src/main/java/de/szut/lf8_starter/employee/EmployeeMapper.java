package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeGetDto;
import de.szut.lf8_starter.employee.dto.EmployeeAddDto;
import org.springframework.stereotype.Service;


@Service
public class EmployeeMapper {
    public EmployeeEntity mapCreateDtoToEntity(EmployeeAddDto dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setRoleId(dto.getRoleId());
        entity.setEmployeeId(dto.getResponsibleEmployeeId());

        return entity;
    }

    public EmployeeGetDto mapEntityToGetDto(EmployeeEntity entity) {
        EmployeeGetDto dto = new EmployeeGetDto();
        dto.setId(entity.getId());
        dto.setRoleId(entity.getRoleId());

        return dto;
    }
}