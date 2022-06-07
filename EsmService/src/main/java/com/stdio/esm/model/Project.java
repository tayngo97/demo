package com.stdio.esm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "project")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @CreationTimestamp
    @Column(name = "start_time",nullable = false,updatable = false)
    private Instant startTime;

    @UpdateTimestamp
    @Column(name = "end_time",nullable = false)
    private Instant endTime;

    @Column(name ="delete_flag")
    private Boolean deleteFlag;

//    @Column(name = "status_id", nullable = false)
//    private Long statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id",referencedColumnName = "id",insertable = false,updatable = false)
    private ProjectStatus projectStatus;

    @OneToMany(mappedBy = "project")
    List<EmployeesProjects> employeesProjects;
}
