package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectEmployeesDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController implements ProjectControllerOpenAPI {
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
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
    public List<ProjectGetDto> getAll() {
        return this.projectService.getAll()
                .stream()
                .map(projectMapper::mapEntityToGetDto)
                .collect(Collectors.toList());
    }

    @Override
    @RequestMapping("/{id}")
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
}