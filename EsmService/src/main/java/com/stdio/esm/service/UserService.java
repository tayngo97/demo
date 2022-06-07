package com.stdio.esm.service;

import com.stdio.esm.exception.EsmException;
import com.stdio.esm.model.Account;
import com.stdio.esm.repository.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Anh Tay
 * @since 06/06/2022
 */
@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void changePassword(Map<String, String> request) {
        LOGGER.info("Change password");
        Long id = Long.parseLong(request.get("id"));
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        String confirmNewPassword = request.get("confirmNewPassword");
        if (!newPassword.equals(confirmNewPassword)) {
            LOGGER.trace("New password and new confirm password not correct");
            throw new EsmException(EsmException.NOT_PASSWORD_MATCH);
        }
        Account account = userRepo.getById(id);
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            LOGGER.trace("Old password is not correct");
            throw new EsmException(EsmException.INCORRECT_PASSWORD);
        }
        LOGGER.info("change password successfully");
        account.setPassword(passwordEncoder.encode(newPassword));
    }

    /***
     * This function for reset password by using username
     * @param userName {@link String}
     * @return {@link Void}
     */
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String userName) throws EsmException {
        LOGGER.info("Reset password");

        Optional<Account> account = userRepo.findByUsername(userName);

        if (account.isEmpty()) {
            LOGGER.info(messageSource.getMessage("message.error.username_not_found", null, null, null));

            throw new EsmException(EsmException.USER_NOT_FOUND);
        } else {
            String newStringPassWord = RandomStringUtils.random(8, true, true);
            String newEncodePassWord = passwordEncoder.encode(newStringPassWord);

            // call sendMail() to send new password to username
            try {
                sendMail(account.get().getUsername(), newStringPassWord);
                LOGGER.info(messageSource.getMessage("message.success.send_mail_reset_password", null, null, null));
            } catch (Exception exception) {
                LOGGER.info(messageSource.getMessage("message.error.send-mail-fail", null, null, null));
            }

            account.get().setPassword(newEncodePassWord);

            LOGGER.info(messageSource.getMessage("message.success.reset_password",
                    null,
                    null,
                    null));
        }
    }

    private void sendMail(String userName, String newPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userName);
        message.setSubject("[Stdio] Reset password successfully");
        message.setText("Please sign in and change this new password: " + newPassword);
        mailSender.send(message);
        LOGGER.info(messageSource.getMessage("message.success.send-mail-reset-password",
                null,
                "DefaultTitle",
                null));
    }
}
