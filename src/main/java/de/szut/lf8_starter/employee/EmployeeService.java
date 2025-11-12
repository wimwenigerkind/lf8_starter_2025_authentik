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
    private final EmployeeClient employeeClient;

    public EmployeeService(EmployeeRepository repository, ProjectRepository projectRepository, EmployeeClient employeeClient) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.employeeClient = employeeClient;
    }

    public EmployeeEntity create(EmployeeEntity entity) {
        return this.repository.save(entity);
    }

    @Transactional
    public void assignToProject(Long projectId, EmployeeAssignmentDto dto) {
        ProjectEntity project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        if (!this.employeeClient.employeeExists(dto.getEmployeeId())) {
            throw new ResourceNotFoundException("Employee not found with id: " + dto.getEmployeeId());
        }

        Long qualificationId;
        try {
            qualificationId = Long.valueOf(dto.getQualification());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Qualification must be a valid number, got: " + dto.getQualification(), e);
        }

        if (!this.employeeClient.isValidQualification(qualificationId)) {
            throw new IllegalArgumentException("Invalid qualification id: " + qualificationId);
        }

        if (project.getQualificationIds() != null && !project.getQualificationIds().isEmpty()) {
            if (!this.employeeClient.employeeHasQualification(dto.getEmployeeId(), project.getQualificationIds())) {
                throw new IllegalArgumentException("Employee " + dto.getEmployeeId() +
                        " does not have the required qualifications for this project");
            }
        }

        long overlappingCount = this.repository.countOverlappingAssignments(
                dto.getEmployeeId(),
                projectId,
                dto.getStartDate(),
                dto.getEndDate()
        );
        if (overlappingCount > 0) {
            throw new IllegalArgumentException("Employee " + dto.getEmployeeId() +
                    " is already assigned to another project during this period");
        }

        EmployeeEntity entity = new EmployeeEntity();
        entity.setProject(project);
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setRoleId(qualificationId);
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        this.repository.save(entity);
    }

    @Transactional
    public void removeFromProject(@NotNull(message = "employee id must not be null") Long employeeId, Long projectId) {
        if (!this.repository.existsByEmployeeIdAndProject_Id(employeeId, projectId)) {
            throw new ResourceNotFoundException("Employee " + employeeId + " is not assigned to project " + projectId);
        }

        this.repository.deleteByEmployeeIdAndProject_Id(employeeId, projectId);
    }
}