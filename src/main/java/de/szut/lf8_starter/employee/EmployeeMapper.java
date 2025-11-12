package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeGetDto;
import de.szut.lf8_starter.employee.dto.EmployeeAddDto;
import org.springframework.stereotype.Service;


@Service
public class EmployeeMapper {
    public EmployeeEntity mapCreateDtoToEntity(EmployeeAddDto dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setRoleId(dto.getRoleId());
        entity.setEmployeeId(dto.getEmployeeId());

        return entity;
    }

    public EmployeeGetDto mapEntityToGetDto(EmployeeEntity entity) {
        EmployeeGetDto dto = new EmployeeGetDto();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setRoleId(entity.getRoleId());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());

        return dto;
    }
}