package com.stdio.esm.controller;


import com.stdio.esm.exception.EsmException;
import com.stdio.esm.model.EsmResponse;
import com.stdio.esm.service.AccountService;
import com.stdio.esm.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Anh Tay
 * @since 06/06/2022
 */
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping(path = "/list-users")
    @ApiOperation(value = "LIST USERS")
    public ResponseEntity<String> getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body("users");
    }

    @PostMapping(path = "/add-user")
    @ApiOperation(value = "ADD USER")
    public ResponseEntity<String> addUser(){
        return ResponseEntity.status(HttpStatus.OK).body("add user");
    }

    @PutMapping(path = "/edit-user")
    @ApiOperation(value = "UPDATE USER")
    public ResponseEntity<String> updateUser(){
        return ResponseEntity.status(HttpStatus.OK).body("update user");
    }

    @DeleteMapping(path = "/delete-user")
    @ApiOperation(value = "DELETE USER")
    public ResponseEntity<String> deleteUser(){
        return ResponseEntity.status(HttpStatus.OK).body("delete user");
    }

    @PostMapping("/change-password")
    @ApiOperation("CHANGE PASSWORD")
    public ResponseEntity<String> changePassword(@NotNull @RequestBody Map<String,String> request) {
        userService.changePassword(request);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping("/reset-password")
    @ApiOperation("RESET PASSWORD")
    public ResponseEntity<Map<String,Object>> resetPassword(@RequestParam(value = "username")String userName) {
        EsmResponse esmResponse = new EsmResponse();
        try {
            userService.resetPassword(userName);
            esmResponse.setStatus(EsmResponse.SUCCESS);
            esmResponse.setMessage(messageSource.getMessage("message.success.send_mail_reset_password",
                    null, null, null));
            esmResponse.setResponseData(null);
        }catch (EsmException exception) {
            esmResponse.setStatus(EsmResponse.ERROR);
            esmResponse.setMessage(exception.getMessage());
            esmResponse.setResponseData(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(esmResponse.getResponse());
    }
}
