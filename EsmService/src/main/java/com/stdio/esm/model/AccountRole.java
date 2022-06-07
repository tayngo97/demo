package com.stdio.esm.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Entity
@Table(name = "accounts_roles")
@Data
public class AccountRole {
    @EmbeddedId
    private AccountRoleId accountRoleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Role role;

    @Embeddable
    @Data
    public static class AccountRoleId implements Serializable {

        private static final long serialVersionUID = -8886468907100754072L;

        @Column(name = "account_id")
        private Long accountId;

        @Column(name = "role_id")
        private Long roleId;
    }

}
