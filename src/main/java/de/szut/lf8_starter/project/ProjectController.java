package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectEmployeesDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import de.szut.lf8_starter.project.dto.ProjectUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @Valid @RequestBody ProjectUpdateDto dto) {
        ProjectEntity existingEntity = this.projectService.getById(id);
        projectMapper.updateEntityFromDto(dto, existingEntity);
        this.projectService.update(existingEntity);
    }
}
