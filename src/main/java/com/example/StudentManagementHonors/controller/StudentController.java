package com.example.StudentManagementHonors.controller;


import com.example.StudentManagementHonors.model.Student;
import com.example.StudentManagementHonors.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student student) {
        try {
            Student registeredStudent = service.registerStudent(student);
            return ResponseEntity.ok(registeredStudent); // Return the Student object
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return the error message from exception
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        try {
            Student student = service.loginStudent(email, password)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
            return ResponseEntity.ok(student); // Return the Student object
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Return the error message dynamically
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = service.updateStudent(id, studentDetails);
            return ResponseEntity.ok(updatedStudent); // Return the updated Student object
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Return the error message dynamically
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        try {
            Student student = service.getStudent(id)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found"));
            return ResponseEntity.ok(student); // Return the Student object
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Return the error message dynamically
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            service.deleteStudent(id); // Assume the service method deletes the student
            return ResponseEntity.ok("Student with ID " + id + " has been successfully deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Return 404 if student not found
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the student."); // Handle other runtime exceptions
        }
    }
}
