package com.example.examJEE.controller;

import com.example.examJEE.model.Employe;
import com.example.examJEE.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employe> employees = employeService.getAllEmployes();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @PostMapping("/removeEmployee")
    public String removeEmployee(@RequestParam("employeeId") Long employeeId) {
        employeService.removeEmploye(employeeId);
        return "redirect:/employees";
    }
}
