package com.example.StudentManagementHonors.controller;

import com.example.StudentManagementHonors.Student;
import com.example.StudentManagementHonors.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentControllers {
    @Autowired
    StudentService studentService;

    @GetMapping("/test")
    public String testStudent(){
        return "Started Test Server";
    }

    // Register
    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        if (studentService.existsByEmail(student.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists.");
        }
        String studentId = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registered Successfully " + studentId);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<String> loginStudent(@RequestParam String email, @RequestParam String password) {
        Student student = studentService.getAllStudents().stream()
                .filter(s -> s.getEmail().equals(email) && s.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Check Credentials");
        }
        return ResponseEntity.ok("Login successful.");
    }

    // all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(students);
    }

    // Get student
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(student);
    }

    // Update student information
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        Student updatedStudentInfo = studentService.updateStudent(id, updatedStudent);
        if (updatedStudentInfo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        return ResponseEntity.ok(updatedStudentInfo);
    }


    // Delete student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        boolean isDeleted = studentService.deleteStudent(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        return ResponseEntity.ok("Deleted Student");
    }

}
