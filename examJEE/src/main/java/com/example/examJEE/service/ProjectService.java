package com.example.examJEE.service;

import com.example.examJEE.model.Employe;
import com.example.examJEE.model.Project;
import com.example.examJEE.repository.EmployeRepository;
import com.example.examJEE.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeRepository employeRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void assignEmployeToProject(Long employeId, Long projectId, double implication) {
        Optional<Employe> employeOpt = employeRepository.findById(employeId);
        Optional<Project> projectOpt = projectRepository.findById(projectId);

        if (employeOpt.isPresent() && projectOpt.isPresent()) {
            Project project = projectOpt.get();
            Employe employe = employeOpt.get();
            String projectNameWithImplication = project.getName() + " (" + implication + "%)";
            project.setName(projectNameWithImplication);
            project.getEmployes().add(employe);
            employe.getProjects().add(project);
            employe.getProjectImplications().put(project, implication);
            projectRepository.save(project);
            employeRepository.save(employe);
        }
    }
}
