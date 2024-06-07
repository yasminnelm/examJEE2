package com.example.examJEE.controller;

import com.example.examJEE.model.Employe;
import com.example.examJEE.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeRestController {

    @Autowired
    private EmployeService employeService;

    @GetMapping
    public List<Employe> listEmployees() {
        return employeService.getAllEmployes();
    }
}

