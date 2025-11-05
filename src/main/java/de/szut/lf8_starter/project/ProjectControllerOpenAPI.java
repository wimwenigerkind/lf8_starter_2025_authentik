package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.dto.EmployeeAddDto;
import de.szut.lf8_starter.employee.dto.EmployeeGetDto;
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
            @ApiResponse(responseCode = "204", description = "added employee to Project",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Project or Employee not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already assigned to Project",
                    content = @Content),
            @ApiResponse(responseCode = "422" , description = "qualification not met",
                    content = @Content)
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeGetDto add(EmployeeAddDto dto, @PathVariable String projectId);
}