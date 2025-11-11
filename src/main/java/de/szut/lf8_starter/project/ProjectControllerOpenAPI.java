package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.dto.EmployeeAssignmentDto;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Adds an employee to a Project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "employee assigned successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "project or employee not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "employee already assigned during this time period",
                    content = @Content),
            @ApiResponse(responseCode = "422", description = "employee has no qualification",
                    content = @Content)})
    @PostMapping("/{projectId}/employees")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void assignEmployee(@PathVariable Long projectId, @Valid @RequestBody EmployeeAssignmentDto dto);

    @Operation(summary = "removes an employee from a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "employee removed successfully",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "project not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "employee not assigned to this project",
                    content = @Content)})
    @DeleteMapping("/{projectId}/employees/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeEmployee(@PathVariable Long projectId, @PathVariable Long employeeId);

    @Operation(summary = "Gets all projects of an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found projects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "employee not found",
                    content = @Content)})
    @GetMapping("/employees/{employeeId}/projects")
    @ResponseStatus(HttpStatus.OK)
    ProjectGetDto getProjectsByEmployeeId(@PathVariable Long employeeId);

    @Operation(summary = "Gets a specific project of an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "employee or project not found",
                    content = @Content)})
    @GetMapping("/employees/{employeeId}/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    ProjectGetDto getProjectByEmployeeIdAndProjectId(@PathVariable Long employeeId, @PathVariable Long projectId);
}