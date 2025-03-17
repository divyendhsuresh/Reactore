package com.reacotre.reactore.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "students")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gradeLevel;
    private Double gpa;
    private String gender;

    @ElementCollection
    private List<String> activities;

}
