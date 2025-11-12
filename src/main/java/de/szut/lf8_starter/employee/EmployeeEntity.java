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

    private Long employeeId;
    private Long roleId;
    private LocalDate startDate;
    private LocalDate endDate;

    public void setRole(@NotBlank(message = "employee role must not be blank") Long role) {
        this.roleId = role;
    }

    public Long getRole() {
        return roleId;
    }
}