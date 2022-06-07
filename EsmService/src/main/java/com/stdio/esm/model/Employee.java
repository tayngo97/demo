package com.stdio.esm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Entity
@Table(name ="employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Min(value = 18, message = "Name should not be less than 3 characters")
    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "address",length = 255)
    private String address;

    @Column(name = "avatar_img")
    private String avatar;

    @Column(name = "start_date")
    private Instant startDate;

    @CreationTimestamp
    @Column(name = "create_at",nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modify_at",nullable = false)
    private Instant modifyAt;

    @Column(name ="delete_flag")
    private Boolean deleteFlag;

    @Column(name = "job_title", length = 255, nullable = false, unique = false)
    private String jobTitle;

    @Email(message = "Email should be valid")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    @Size(min = 10, max = 10, message
            = "Phone must be between 10 characters")
    @Column(name = "phone_number", length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @Size(min = 10, max = 255, message
            = "Place of birth must be between 10 and 255 characters")
    @Column(name = "place_of_birth", length = 255, nullable = false, unique = false)
    private String placeOfBirth;

    @Size(min = 10, max = 50, message
            = "Nationality must be between 10 and 50 characters")
    @Column(name = "nationality", length = 30, nullable = true, unique = false)
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "website", nullable = true, unique = false)
    private String website;

    @Column(name = "professional_summary ", nullable = true, unique = false)
    private String professionalSummary ;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "employee", cascade = CascadeType.ALL)
    List<EmployeeSkills> employeeSkills;

    @OneToMany(mappedBy = "employee")
    List<EmployeesProjects> employeesProjects;

    @OneToMany(mappedBy = "employee")
    List<Education> educations;

    @OneToOne(mappedBy = "employee")
    private Account account;

    @OneToOne(mappedBy = "employee")
    private EmploymentHistory employmentHistories;

    private enum Gender {
        MALE, FEMALE
    }
}
