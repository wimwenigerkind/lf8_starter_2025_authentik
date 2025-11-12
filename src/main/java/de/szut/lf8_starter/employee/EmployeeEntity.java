package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.project.ProjectEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Represents an employee assignment to a project.
 * This is a join entity that connects projects with employees from an external employee service.
 */
@Data
@Entity
@Table(name = "project_employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    /**
     * Reference to the external employee ID from the employee service.
     * This is NOT a foreign key to a local employee table.
     */
    private Long employeeId;

    /**
     * The qualification/role ID that the employee has for this specific project assignment.
     * References a qualification from the external employee service.
     */
    private Long roleId;

    private LocalDate startDate;
    private LocalDate endDate;

}