package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeAssignmentDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public EmployeeEntity create(EmployeeEntity entity) {
        return this.repository.save(entity);
    }

    public void assignToProject(@NotNull(message = "employee id must not be null") Long responsibleEmployeeId, Long projectId, EmployeeAssignmentDto dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setResponsibleEmployeeId(responsibleEmployeeId);
        entity.setRoleId(Long.valueOf(dto.getQualification()));
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        this.repository.save(entity);
    }

    public void removeFromProject(@NotNull(message = "employee id must not be null") Long employeeId, Long projectId) {
        this.repository.deleteById(employeeId);
    }
}