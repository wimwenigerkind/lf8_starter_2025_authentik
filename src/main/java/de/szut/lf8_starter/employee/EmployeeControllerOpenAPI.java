package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeAddDto;
import de.szut.lf8_starter.employee.dto.EmployeeGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
