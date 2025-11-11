package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.employee.dto.EmployeeAddDto;
import de.szut.lf8_starter.employee.dto.EmployeeGetDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController implements EmployeeControllerOpenAPI {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @PostMapping
    public EmployeeGetDto create(@Valid @RequestBody EmployeeAddDto dto) {
        EmployeeEntity entity = employeeMapper.mapCreateDtoToEntity(dto);
        EmployeeEntity savedEntity = this.employeeService.create(entity);
        return employeeMapper.mapEntityToGetDto(savedEntity);
    }
}
