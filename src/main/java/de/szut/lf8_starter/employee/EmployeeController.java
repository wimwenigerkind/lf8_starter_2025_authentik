package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeAddDto;
import de.szut.lf8_starter.employee.dto.EmployeeGetDto;
import de.szut.lf8_starter.project.ProjectEntity;
import de.szut.lf8_starter.project.ProjectMapper;
import de.szut.lf8_starter.project.ProjectService;
import de.szut.lf8_starter.project.dto.ProjectEmployeesDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController implements EmployeeControllerOpenAPI {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper,
                              ProjectService projectService, ProjectMapper projectMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.projectService = projectService;
        this.projectMapper = projectMapper;
    }

    @Override
    @PostMapping
    public EmployeeGetDto create(@Valid @RequestBody EmployeeAddDto dto) {
        EmployeeEntity entity = employeeMapper.mapCreateDtoToEntity(dto);
        EmployeeEntity savedEntity = this.employeeService.create(entity);
        return employeeMapper.mapEntityToGetDto(savedEntity);
    }

    @Override
    @GetMapping("/{employeeId}/projects")
    public List<ProjectEmployeesDto> getProjectsByEmployeeId(@PathVariable Long employeeId) {
        List<ProjectEntity> projects = this.projectService.getProjectsByEmployeeId(employeeId);
        return projects.stream()
                .map(projectMapper::mapEntityToEmployeesDto)
                .toList();
    }

    @Override
    @GetMapping("/{employeeId}/projects/{projectId}")
    public ProjectEmployeesDto getProjectByEmployeeIdAndProjectId(@PathVariable Long employeeId, @PathVariable Long projectId) {
        ProjectEntity project = this.projectService.getProjectByEmployeeIdAndProjectId(employeeId, projectId);
        return projectMapper.mapEntityToEmployeesDto(project);
    }
}
