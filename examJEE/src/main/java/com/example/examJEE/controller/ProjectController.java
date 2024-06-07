package com.example.examJEE.controller;

import com.example.examJEE.model.Employe;
import com.example.examJEE.model.Project;
import com.example.examJEE.service.EmployeService;
import com.example.examJEE.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeService employeService;

    @GetMapping("/assign")
    public String showAssignForm(Model model) {
        List<Employe> employes = employeService.getAllEmployes();
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("employes", employes);
        model.addAttribute("projects", projects);
        return "assignForm";
    }

    @PostMapping("/assign")
    public String assignProject(@RequestParam("employeId") Long employeId,
                                @RequestParam("projectId") Long projectId,
                                @RequestParam("implication") double implication) {
        projectService.assignEmployeToProject(employeId, projectId, implication);
        return "redirect:/home";
    }
}
