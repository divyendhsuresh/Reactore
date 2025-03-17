package com.reacotre.reactore.Controller;


import com.reacotre.reactore.Entity.*;
import com.reacotre.reactore.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/gender-count")
    public ResponseEntity<StudentContainer> getGenderCount() {
        StudentContainer result = studentService.countMaleAndFemaleStudents();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/add-prefix")
    public ResponseEntity<List<Student>> updateStudentNames() {
        List<Student> updatedStudents = studentService.addPrefixToStudentNames();
        return ResponseEntity.ok(updatedStudents);
    }

    @GetMapping("/grade-level-count")
    public ResponseEntity<List<GradeLevelContainer>> getGradeLevelCount() {
        List<GradeLevelContainer> gradeLevelCounts = studentService.countStudentsByGradeLevel();
        return ResponseEntity.ok(gradeLevelCounts);
    }

    @GetMapping("/activity-count")
    public ResponseEntity<List<ActivityContainer>> getActivityCount() {
        List<ActivityContainer> activityCounts = studentService.countStudentsByActivity();
        return ResponseEntity.ok(activityCounts);
    }

    @GetMapping("/performance-grouping-async")
    public CompletableFuture<ResponseEntity<List<PerformanceContainer>>> getPerformanceGroupingAsync() {
        return studentService.groupStudentsByPerformanceAsync()
                .thenApply(ResponseEntity::ok);
    }

}
