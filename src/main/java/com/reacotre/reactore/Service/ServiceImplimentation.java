package com.reacotre.reactore.Service;


import com.reacotre.reactore.Entity.*;
import com.reacotre.reactore.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ServiceImplimentation  implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentContainer countMaleAndFemaleStudents() {
        List<Student> students = studentRepository.findAll();
        long maleCount = students.stream()
                .filter(student -> "male".equalsIgnoreCase(student.getGender()))
                .count();
        long femaleCount = students.stream()
                .filter(student -> "female".equalsIgnoreCase(student.getGender()))
                .count();
        return new StudentContainer(maleCount, femaleCount);
    }

    @Override
    public List<Student> addPrefixToStudentNames() {
        List<Student> students = studentRepository.findAll();
        students.forEach(student -> {
            if ("male".equalsIgnoreCase(student.getGender()) && !student.getName().startsWith("Mr.")) {
                student.setName("Mr. " + student.getName());
            } else if ("female".equalsIgnoreCase(student.getGender()) && !student.getName().startsWith("Ms.")) {
                student.setName("Ms. " + student.getName());
            }
        });
        studentRepository.saveAll(students);
        return students;
    }

    @Override
    public List<GradeLevelContainer> countStudentsByGradeLevel() {
        List<Student> students = studentRepository.findAll();
        // Group students by their grade level and count them
        Map<String, Long> groupedByGrade = students.stream()
                .collect(Collectors.groupingBy(Student::getGradeLevel, Collectors.counting()));

        // Convert each group into a GradeLevelContainer object
        return groupedByGrade.entrySet().stream()
                .map(entry -> new GradeLevelContainer(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityContainer> countStudentsByActivity() {
        List<Student> students = studentRepository.findAll();
        // Flatten all activities from each student and group them by activity name
        Map<String, Long> activityCounts = students.stream()
                .filter(student -> student.getActivities() != null)
                .flatMap(student -> student.getActivities().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return activityCounts.entrySet().stream()
                .map(entry -> new ActivityContainer(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public CompletableFuture<List<PerformanceContainer>> groupStudentsByPerformanceAsync() {
        return CompletableFuture.supplyAsync(() -> {
            // Fetch all students from the repository
            List<Student> students = studentRepository.findAll();

            //use streams
            Map<String, Long> performanceCounts = students.stream()
                    .collect(Collectors.groupingBy(student -> {
                        double gpa = student.getGpa();
                        if (gpa >= 0.0 && gpa <= 4.0) {
                            return "Poor";
                        } else if (gpa >= 4.1 && gpa <= 7.0) {
                            return "Average";
                        } else if (gpa >= 7.1) {
                            return "Excellent";
                        } else {
                            return "Unknown";
                        }
                    }, Collectors.counting()));

            // Map the grouped result to a list of PerformanceContainer objects
            return performanceCounts.entrySet().stream()
                    .map(entry -> new PerformanceContainer(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        });
    }
}
