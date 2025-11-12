package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeAddDto;
import de.szut.lf8_starter.employee.dto.EmployeeGetDto;
import de.szut.lf8_starter.project.dto.ProjectEmployeesDto;
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

public interface EmployeeControllerOpenAPI {
    @Operation(summary = "creates a new Project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added Employee",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeGetDto create(@Valid @RequestBody EmployeeAddDto dto);

    @Operation(summary = "Gets all projects of an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found projects",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProjectEmployeesDto.class)))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "employee not found",
                    content = @Content)})
    @GetMapping("/{employeeId}/projects")
    List<ProjectEmployeesDto> getProjectsByEmployeeId(@PathVariable Long employeeId);

    @Operation(summary = "Gets a specific project of an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectEmployeesDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "employee or project not found",
                    content = @Content)})
    @GetMapping("/{employeeId}/projects/{projectId}")
    ProjectEmployeesDto getProjectByEmployeeIdAndProjectId(@PathVariable Long employeeId, @PathVariable Long projectId);
}
