package com.stdio.esm.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employees_projects")
@Data
public class EmployeesProjects {
    @EmbeddedId
    private EmployeesProjectsId employeesProjectsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Project project;


    @Embeddable
    @Data
    public static class EmployeesProjectsId implements Serializable {

        private static final long serialVersionUID = -8886468907100754072L;

        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "project_id")
        private Long projectId;
    }
}
