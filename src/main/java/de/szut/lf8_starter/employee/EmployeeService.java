package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeAssignmentDto;
import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_starter.project.ProjectEntity;
import de.szut.lf8_starter.project.ProjectRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private final ProjectRepository projectRepository;

    public EmployeeService(EmployeeRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    public EmployeeEntity create(EmployeeEntity entity) {
        return this.repository.save(entity);
    }

    @Transactional
    public void assignToProject(Long projectId, EmployeeAssignmentDto dto) {
        ProjectEntity project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        EmployeeEntity entity = new EmployeeEntity();
        entity.setProject(project);
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setRoleId(Long.valueOf(dto.getQualification()));
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        this.repository.save(entity);
    }

    @Transactional
    public void removeFromProject(@NotNull(message = "employee id must not be null") Long employeeId, Long projectId) {
        this.repository.deleteByEmployeeIdAndProjectId(employeeId, projectId);
    }
}