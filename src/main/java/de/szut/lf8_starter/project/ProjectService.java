package de.szut.lf8_starter.project;

import de.szut.lf8_starter.client.ClientClient;
import de.szut.lf8_starter.employee.EmployeeClient;
import de.szut.lf8_starter.exceptionHandling.ClientNotFoundException;
import de.szut.lf8_starter.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_starter.exceptionHandling.QualificationNotMetException;
import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (!employeeClient.employeeExists(entity.getResponsibleEmployeeId())) {
            throw new EmployeeNotFoundException("Employee not found with id: " + entity.getResponsibleEmployeeId());
        }

        // Validate client exists - Dummy validation (Issue #9)
        if (!clientClient.clientExists(entity.getClientId())) {
            throw new ClientNotFoundException("Client not found with id: " + entity.getClientId());
        }

        if (entity.getQualificationIds() != null && !entity.getQualificationIds().isEmpty()) {
            for (Long qualificationId : entity.getQualificationIds()) {
                if (!employeeClient.isValidQualification(qualificationId)) {
                    throw new QualificationNotMetException("Qualification not found with id: " + qualificationId);
                }
            }
        }

        if (!employeeClient.employeeHasQualification(entity.getResponsibleEmployeeId(), entity.getQualificationIds())) {
            throw new QualificationNotMetException("Employee does not have all required qualifications for this project");
        }

        return this.repository.save(entity);
    }

    public List<ProjectEntity> getAll() {
        return this.repository.findAll();
    }

    public ProjectEntity getById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }
}
