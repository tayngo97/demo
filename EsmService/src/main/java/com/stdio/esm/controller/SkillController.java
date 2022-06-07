package com.stdio.esm.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/skills")
public class SkillController {

    @GetMapping(path = "/list-skills")
    @ApiOperation(value = "LIST SKILLS")
    public ResponseEntity<String> getSkills(){
    return ResponseEntity.status(HttpStatus.OK).body("skill");
    }

    @PostMapping(path = "/add-skill")
    @ApiOperation(value = "ADD SKILLS")
    public ResponseEntity<String> addSkill(){
        return ResponseEntity.status(HttpStatus.OK).body("add skills");
    }

    @PutMapping(path = "/edit-skill")
    @ApiOperation(value = "EDIT SKILLS")
    public ResponseEntity<String> updateSkill(){
        return ResponseEntity.status(HttpStatus.OK).body("update skills");
    }

    @DeleteMapping(path = "/delete-skill")
    @ApiOperation(value = "DELETE SKILLS")
    public ResponseEntity<String> deleteSkill(){
        return ResponseEntity.status(HttpStatus.OK).body("delete skills");
    }
}
