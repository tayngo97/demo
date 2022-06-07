package com.stdio.esm.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "skill")
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

    @CreationTimestamp
    @Column(name = "create_at",nullable = false,updatable = false)
    private Instant created_at;

    @UpdateTimestamp
    @Column(name = "modify_at",nullable = false)
    private Instant modify_at;

    @Column(name ="delete_flag")
    private Boolean delete_flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_type_id",insertable = false,updatable = false)
    private SkillTypes skillTypes;
}
