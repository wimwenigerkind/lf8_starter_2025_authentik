package de.szut.lf8_starter.project;

import de.szut.lf8_starter.client.ClientClient;
import de.szut.lf8_starter.employee.EmployeeClient;
import de.szut.lf8_starter.exceptionHandling.ClientNotFoundException;
import de.szut.lf8_starter.exceptionHandling.ConflictException;
import de.szut.lf8_starter.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_starter.exceptionHandling.QualificationNotMetException;
import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository repository;
    private final EmployeeClient employeeClient;
    private final ClientClient clientClient;

    public ProjectService(ProjectRepository repository, EmployeeClient employeeClient, ClientClient clientClient) {
        this.repository = repository;
        this.employeeClient = employeeClient;
        this.clientClient = clientClient;
    }

    public ProjectEntity create(ProjectEntity entity) {
        validateProjectDependencies(entity.getResponsibleEmployeeId(), entity.getClientId(), entity.getQualificationIds());
        return this.repository.save(entity);
    }

    public List<ProjectEntity> getAll() {
        return this.repository.findAll(Sort.by("id"));
    }

    public ProjectEntity getById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    @Transactional
    public void update(Long id, ProjectEntity updatedEntity) {
        if (!updatedEntity.getId().equals(id)) {
            throw new IllegalArgumentException("ID mismatch: path ID " + id + " does not match entity ID " + updatedEntity.getId());
        }

        validateProjectDependencies(updatedEntity.getResponsibleEmployeeId(), updatedEntity.getClientId(), updatedEntity.getQualificationIds());
        this.repository.save(updatedEntity);
    }

    private void validateProjectDependencies(Long employeeId, Long clientId, List<Long> qualificationIds) {
        if (!employeeClient.employeeExists(employeeId)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }

        if (!clientClient.clientExists(clientId)) {
            throw new ClientNotFoundException("Client not found with id: " + clientId);
        }

        if (qualificationIds != null && !qualificationIds.isEmpty()) {
            for (Long qualificationId : qualificationIds) {
                if (!employeeClient.isValidQualification(qualificationId)) {
                    throw new QualificationNotMetException("Qualification not found with id: " + qualificationId);
                }
            }
        }

        if (!employeeClient.employeeHasQualification(employeeId, qualificationIds)) {
            throw new QualificationNotMetException("Employee does not have all required qualifications for this project");
        }
    }

    public void deleteById(Long id) {
        ProjectEntity project = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        if (project.getEmployees() != null && !project.getEmployees().isEmpty()) {
            throw new ConflictException("Cannot delete project with assigned employees");
        }

        this.repository.delete(project);
    }

    public List<ProjectEntity> getProjectsByEmployeeId(Long employeeId) {
        return this.repository.findAll().stream()
                .filter(project -> project.getEmployees() != null &&
                        project.getEmployees().stream()
                                .anyMatch(employee -> employee.getEmployeeId().equals(employeeId)))
                .toList();
    }

    public ProjectEntity getProjectByEmployeeIdAndProjectId(Long employeeId, Long projectId) {
        ProjectEntity project = this.repository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        boolean employeeAssigned = project.getEmployees() != null &&
                project.getEmployees().stream()
                        .anyMatch(employee -> employee.getEmployeeId().equals(employeeId));

        if (!employeeAssigned) {
            throw new ResourceNotFoundException("Employee with id " + employeeId + " is not assigned to project with id " + projectId);
        }

        return project;
    }
}
