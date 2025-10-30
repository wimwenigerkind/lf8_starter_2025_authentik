package de.szut.lf8_starter.project;

import de.szut.lf8_starter.client.ClientClient;
import de.szut.lf8_starter.employee.EmployeeClient;
import de.szut.lf8_starter.exceptionHandling.ClientNotFoundException;
import de.szut.lf8_starter.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_starter.exceptionHandling.QualificationMissingException;
import org.springframework.stereotype.Service;

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

        if (!employeeClient.employeeHasQualification(entity.getResponsibleEmployeeId())) {
            throw new QualificationMissingException("Employee does not have any qualification");
        }

        return this.repository.save(entity);
    }
}
