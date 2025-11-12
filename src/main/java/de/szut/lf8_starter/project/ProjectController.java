package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeService;
import de.szut.lf8_starter.employee.dto.EmployeeAssignmentDto;
import de.szut.lf8_starter.exceptionHandling.ProjectNotFoundException;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectEmployeesDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import de.szut.lf8_starter.project.dto.ProjectUpdateDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
public abstract class ProjectController implements ProjectControllerOpenAPI {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final EmployeeService employeeService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, EmployeeService employeeService) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.employeeService = employeeService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectGetDto create(@Valid @RequestBody ProjectCreateDto dto) {
        ProjectEntity entity = projectMapper.mapCreateDtoToEntity(dto);
        ProjectEntity savedEntity = this.projectService.create(entity);
        return projectMapper.mapEntityToGetDto(savedEntity);
    }

    @Override
    @GetMapping
    public List<ProjectGetDto> getAll() {
        return this.projectService.getAll()
                .stream()
                .map(projectMapper::mapEntityToGetDto)
                .toList();
    }

    @Override
    @GetMapping("/{id}")
    public ProjectGetDto getById(@PathVariable long id) {
        ProjectEntity entity = this.projectService.getById(id);
        return projectMapper.mapEntityToGetDto(entity);
    }

    @Override
    @GetMapping("/{id}/employees")
    public ProjectEmployeesDto getProjectEmployees(@PathVariable long id) {
        ProjectEntity entity = this.projectService.getById(id);
        return projectMapper.mapEntityToEmployeesDto(entity);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        this.projectService.deleteById(id);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @Valid @RequestBody ProjectUpdateDto dto) {
        ProjectEntity existingEntity = this.projectService.getById(id);
        projectMapper.updateEntityFromDto(dto, existingEntity);
        this.projectService.update(existingEntity);
    }

    @PostMapping("/{projectId}/employees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignEmployee(@PathVariable Long projectId, @RequestBody EmployeeAssignmentDto dto) {
        employeeService.assignToProject(dto.getEmployeeId(), dto);
    }

    @Override
    @DeleteMapping("/{projectId}/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployee(@PathVariable Long projectId, @PathVariable Long employeeId) {
        employeeService.removeFromProject(employeeId, projectId);
    }

    @GetMapping("/employees/{employeeId}/projects")
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectGetDto> getProjectsByEmployeeId(@PathVariable Long employeeId, Long projectId) {
        List<ProjectEntity> projects = projectService.getProjectsByEmployeeId(employeeId, projectId);
        return projects.stream()
                .map(projectMapper::mapEntityToGetDto)
                .toList();
    }

    @GetMapping("/employees/{employeeId}/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProjectGetDto> getProjectByEmployeeIdAndProjectId(@PathVariable Long employeeId, @PathVariable Long projectId) {
        ProjectEntity project = projectService.getById(projectId);
        boolean assigned = project.getEmployees() != null &&
                project.getEmployees().stream().anyMatch(e -> e.getId().equals(employeeId));
        if (assigned) {
            return ResponseEntity.ok(projectMapper.mapEntityToGetDto(project));
        }
        throw new ProjectNotFoundException("Project not found for the given employee.");
    }
}
