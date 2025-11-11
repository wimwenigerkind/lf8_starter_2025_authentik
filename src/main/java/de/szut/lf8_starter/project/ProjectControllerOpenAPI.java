package de.szut.lf8_starter.project;

import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectEmployeesDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import de.szut.lf8_starter.project.dto.ProjectUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProjectControllerOpenAPI {
    @Operation(summary = "creates a new Project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created Project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProjectGetDto create(@Valid @RequestBody ProjectCreateDto dto);

    @Operation(summary = "get all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returned all projects",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectGetDto.class)))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping
    List<ProjectGetDto> getAll();

    @Operation(summary = "get project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returned project by id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "project not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping("/{id}")
    ProjectGetDto getById(@PathVariable("id") long id);

    @Operation(summary = "get employees of a project by project id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returned project employees",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectEmployeesDto.class))}),
            @ApiResponse(responseCode = "404", description = "project not found",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping("/{id}/employees")
    ProjectEmployeesDto getProjectEmployees(@PathVariable("id") long id);

    @Operation(summary = "delete project by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "project deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "project not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "project has assigned employees",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") long id);
  
    @Operation(summary = "update an existing project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "project updated successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "project not found",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "validation error (e.g. end date before start date)",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable("id") long id, @Valid @RequestBody ProjectUpdateDto dto);
}
