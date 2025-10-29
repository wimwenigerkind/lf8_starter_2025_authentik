package de.szut.lf8_starter.project;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "project_employee")
public class ProjectEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    private long employeeId;
    private long roleId;
    private LocalDate startDate;
    private LocalDate endDate;
}