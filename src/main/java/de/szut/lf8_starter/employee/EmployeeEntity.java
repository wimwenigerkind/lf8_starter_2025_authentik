package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.project.ProjectEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

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

    private long employeeId;
    private long roleId;
    private LocalDate startDate;
    private LocalDate endDate;

    public void setResponsibleEmployeeId(@NotNull(message = "responsible employee id must not be null") Long responsibleEmployeeId) {
        this.employeeId = responsibleEmployeeId;
    }

    public void setRole(@NotBlank(message = "employee role must not be blank") String role) {
        this.roleId = role.hashCode();
    }

    public String getName() {
        return "" + employeeId;
    }

    public String getRole() {
        return "" + roleId;
    }
}