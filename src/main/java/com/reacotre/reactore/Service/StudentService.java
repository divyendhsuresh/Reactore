package com.reacotre.reactore.Service;

import com.reacotre.reactore.Entity.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface StudentService {
    StudentContainer countMaleAndFemaleStudents();

    List<Student> addPrefixToStudentNames();

    List<GradeLevelContainer> countStudentsByGradeLevel();

    List<ActivityContainer> countStudentsByActivity();

    CompletableFuture<List<PerformanceContainer>> groupStudentsByPerformanceAsync();

}
