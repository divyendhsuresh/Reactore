package com.reacotre.reactore.Service;

import com.reacotre.reactore.Entity.*;

import java.util.List;

public interface StudentService {
    StudentContainer countMaleAndFemaleStudents();

    List<Student> addPrefixToStudentNames();

    List<GradeLevelContainer> countStudentsByGradeLevel();

    List<ActivityContainer> countStudentsByActivity();

    List<PerformanceContainer> groupStudentsByPerformance();

}
