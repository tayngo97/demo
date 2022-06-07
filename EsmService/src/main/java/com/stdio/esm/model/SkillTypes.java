package com.stdio.esm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "skill_types")
@Data
public class SkillTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @CreationTimestamp
    @Column(name = "create_at",nullable = false,updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    @Column(name = "modify_at",nullable = false)
    private Instant modify_at;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "skillTypes", cascade = CascadeType.ALL)
    List<Skill> skills;

    @OneToMany(mappedBy = "account")
    List<AccountRole> accountRoleList;
}
