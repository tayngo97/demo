package com.stdio.esm.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/projects")
public class ProjectController {

    @GetMapping(path = "/list-projects")
    @ApiOperation(value = "LIST PROJECTS")
    public ResponseEntity<String> getProjects() {
        return ResponseEntity.status(HttpStatus.OK).body("projects");
    }

    @PostMapping(path = "/add-project")
    @ApiOperation(value = "ADD PROJECTS")
    public ResponseEntity<String> addProject() {
        return ResponseEntity.status(HttpStatus.OK).body("add project");
    }


    @PutMapping(path = "/edit-project")
    @ApiOperation(value = "UPDATE PROJECTS")
    public ResponseEntity<String> updateProject() {
        return ResponseEntity.status(HttpStatus.OK).body("update project");
    }

    @DeleteMapping(path = "/delete-project")
    @ApiOperation(value = "DELETE PROJECTS")
    public ResponseEntity<String> deleteProject() {
        return ResponseEntity.status(HttpStatus.OK).body("delete project");
    }
}
