package com.stdio.esm.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/levels")
public class LevelController {

    @GetMapping(path = "/list-levels")
    @ApiOperation(value = "LIST LEVELS")
    public ResponseEntity<String> getLevels(){
        return ResponseEntity.status(HttpStatus.OK).body("levels");
    }

}
