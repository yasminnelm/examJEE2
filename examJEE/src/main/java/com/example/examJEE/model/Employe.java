package com.example.examJEE.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    @ElementCollection
    private List<String> skills;
    @ManyToMany(mappedBy = "employes")
    private List<Project> projects;

    @ElementCollection
    @CollectionTable(name = "employe_project_implication", joinColumns = @JoinColumn(name = "employe_id"))
    @MapKeyJoinColumn(name = "project_id")
    @Column(name = "implication")
    private Map<Project, Double> projectImplications = new HashMap<>();
}
