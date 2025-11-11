package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeService;
import de.szut.lf8_starter.employee.dto.EmployeeAssignmentDto;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController implements ProjectControllerOpenAPI {
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

    @PostMapping("/{projectId}/employees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignEmployee(@PathVariable Long projectId, @RequestBody EmployeeAssignmentDto dto) {
        employeeService.assignToProject(dto.getEmployeeId(), projectId, dto);
    }

    @Override
    @DeleteMapping("/{projectId}/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployee(Long projectId, Long employeeId) {
        employeeService.removeFromProject(employeeId, projectId);
    }

    @GetMapping("/employees/{employeeId}/projects")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProjectGetDto getProjectsByEmployeeId(@PathVariable Long employeeId) {
        ProjectEntity[] projects = projectService.getProjectsByEmployeeId(employeeId);
        ProjectGetDto[] result = new ProjectGetDto[projects.length];
        for (int i = 0; i < projects.length; i++) {
            result[i] = projectMapper.mapEntityToGetDto(projects[i]);
        } return null;
    }

    @GetMapping("/employees/{employeeId}/projects/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ProjectGetDto getProjectByEmployeeIdAndProjectId(@PathVariable Long employeeId, @PathVariable Long projectId) {
        ProjectEntity[] projects = projectService.getProjectsByEmployeeId(employeeId);
        for (ProjectEntity project : projects) {
            if (project.getId().equals(projectId)) {
                return projectMapper.mapEntityToGetDto(project);
            }
        } return null;
    }
}