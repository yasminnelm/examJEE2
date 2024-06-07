package com.example.examJEE.controller.rest;


import com.example.examJEE.model.Project;
import com.example.examJEE.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> listProjects() {
        return projectService.getAllProjects();
    }
}
