package com.stdio.esm.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Entity
@Table(name = "account")
@Data
public class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", length = 255, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "create_at",nullable = false,updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modify_at",nullable = false)
    private Instant modifyAt;

    @Column(name ="delete_flag")
    private Boolean deleteFlag;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
    List<AccountRole> accountRoleList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;
}
