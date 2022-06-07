package com.stdio.esm.repository;

import com.stdio.esm.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    /**
     * Get account from username
     *
     * @param name {@link String}
     * @return {@link Optional}
     */
    Optional<Account> findByUsername(String name);

    /**
     * Get account from username IgnoreCase
     *
     * @param username {@link String}
     * @return {@link Optional}
     */
    Optional<Account> findByUsernameIgnoreCase(String username);
}
