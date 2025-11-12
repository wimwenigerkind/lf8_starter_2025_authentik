package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.project.ProjectEntity;
import jakarta.persistence.*;
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

    private Long responsibleEmployeeId;
    private Long roleId;
    private Long projectId;
    private LocalDate startDate;
    private LocalDate endDate;

}