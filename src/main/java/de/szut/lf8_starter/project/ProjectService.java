package de.szut.lf8_starter.project;

import de.szut.lf8_starter.exceptionHandling.BadRequestException;
import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public ProjectEntity create(ProjectEntity entity) {
        return this.repository.save(entity);
    }

    public void delete(Long id) {
        ProjectEntity entity = this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + " not found"));

        // TODO: change status code later
        if (entity.getEmployees() != null && !entity.getEmployees().isEmpty()) {
            throw new BadRequestException("Cannot delete project with id " + id + " because it has employees assigned");
        }

        this.repository.delete(entity);
    }
}
