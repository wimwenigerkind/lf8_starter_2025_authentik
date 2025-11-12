package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeAssignmentDto;
import jakarta.transaction.Transactional;
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

    public void assignToProject(@NotNull(message = "employee id must not be null") Long responsibleEmployeeId, EmployeeAssignmentDto dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmployeeId(responsibleEmployeeId);
        entity.setRoleId(Long.valueOf(dto.getQualification()));
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        this.repository.save(entity);
    }

    @Transactional
    public void removeFromProject(@NotNull(message = "employee id must not be null") Long responsibleEmployeeId, Long projectId) {
        this.repository.deleteByEmployeeIdAndProjectId(responsibleEmployeeId, projectId);
    }
}