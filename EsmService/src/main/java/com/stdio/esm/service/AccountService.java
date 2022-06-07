package com.stdio.esm.service;

import com.stdio.esm.exception.EsmException;
import com.stdio.esm.model.Account;
import com.stdio.esm.repository.AccountRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    /**
     * Change the password of the person who is logged in
     *
     * @param request (old password, new password, confirm password) {@link Map<String,String>}
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void changePassword(Map<String, String> request) {
        LOGGER.info("Change password");

        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        String confirmNewPassword = request.get("confirmNewPassword");

        if (!newPassword.equals(confirmNewPassword)) {
            LOGGER.trace("New password and new confirm password not correct");
            throw new EsmException(EsmException.NOT_PASSWORD_MATCH);
        }
        UserDetails esmUserDetail = getCurrentAccountOrElseThrow();
        Account account = accountRepo.findByUsernameIgnoreCase(esmUserDetail.getUsername()).get();
        LOGGER.info("{} change password", account.getUsername());
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            throw new EsmException(EsmException.INCORRECT_PASSWORD);
        }

        LOGGER.info("change password successfully");
        account.setPassword(passwordEncoder.encode(newPassword));
    }

    /**
     * Get current login user from securityContext
     *
     * @return {@link UserDetails}
     */
    private Optional<UserDetails> getCurrentAccountLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
            if (authentication.getPrincipal() instanceof UserDetails) {
                return (UserDetails) authentication.getPrincipal();
            }
            return null;
        });
    }

    /**
     * Returns the current user if any, otherwise returns null
     *
     * @return {@link UserDetails}
     */
    private UserDetails getCurrentAccountOrElseThrow() {
        return getCurrentAccountLogin().orElseThrow(() -> {
            LOGGER.error("no users in thread local");
            throw new EsmException(EsmException.USER_NOT_FOUND);
        });
    }


    /**
     * @param username the username identifying the user whose data is required.
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("Load user: {}", username);
        Account account = accountRepo.findByUsername(username).orElseThrow(() -> new EsmException(EsmException.USER_NOT_FOUND));
        if (account.getDeleteFlag()) {
            LOGGER.error("user was deleted {}", username);
            throw new EsmException(EsmException.BAD_REQUEST);
        }
        Set<GrantedAuthority> authorityList = account.getAccountRoleList()
                .stream().map(tmp -> new SimpleGrantedAuthority(tmp.getRole().getName()))
                .collect(Collectors.toSet());
        LOGGER.info("get user succesfully", username);
        return new User(account.getUsername(), account.getPassword(), authorityList);
    }
}
