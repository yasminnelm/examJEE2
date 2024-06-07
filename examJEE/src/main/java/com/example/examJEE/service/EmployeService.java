package com.example.examJEE.service;

import com.example.examJEE.model.Employe;
import com.example.examJEE.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;

    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    public void removeEmploye(Long employeeId) {
        employeRepository.deleteById(employeeId);
    }
}
