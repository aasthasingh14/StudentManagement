package com.example.StudentManagementHonors.service;

import com.example.StudentManagementHonors.model.Student;
import com.example.StudentManagementHonors.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public Student registerStudent(Student student) {
        if (repository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use!");
        }
        return repository.save(student);
    }

    public Optional<Student> loginStudent(String email, String password) {
        return repository.findByEmail(email)
                .filter(student -> student.getPassword().equals(password));
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setAddress(studentDetails.getAddress());
        student.setPhoneNumber(studentDetails.getPhoneNumber());
        return repository.save(student);
    }

    public Optional<Student> getStudent(Long id) {
        return repository.findById(id);
    }

    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Student with ID " + id + " does not exist.");
        }
        repository.deleteById(id);
    }
}
