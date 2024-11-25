package com.example.StudentManagementHonors.service;

import com.example.StudentManagementHonors.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private HashMap<String, Student> students;

    @PostConstruct
    public void setup() {
        students = new HashMap<>();
    }

    // Create a new student
    public String createStudent(Student student) {
        student.setStudentId(UUID.randomUUID().toString());
        students.put(student.getStudentId(), student);
        return student.getStudentId();
    }

    // Retrieve all students
    public List<Student> getAllStudents() {
        return students.values().stream().collect(Collectors.toList());
    }

    // Retrieve a student by ID
    public Student getStudent(String id) {
        return students.getOrDefault(id, null);
    }

    // Update an existing student
    public Student updateStudent(String id, Student updatedStudent) {
        // Assume `students` is a Map<String, Student> or a database retrieval method
        Student existingStudent = students.get(id); // Retrieve the student by ID

        if (existingStudent == null) {
            return null; // Return null if the student does not exist
        }

        // Update only the fields provided in `updatedStudent`
        if (updatedStudent.getName() != null) {
            existingStudent.setName(updatedStudent.getName());
        }
        if (updatedStudent.getEmail() != null) {
            existingStudent.setEmail(updatedStudent.getEmail());
        }
        if (updatedStudent.getPhone() != null) {
            existingStudent.setPhone(updatedStudent.getPhone());
        }
        if (updatedStudent.getPassword() != null) {
            existingStudent.setPassword(updatedStudent.getPassword());
        }
        if (updatedStudent.getAddress() != null) {
            existingStudent.setAddress(updatedStudent.getAddress());
        }

        // Save the updated student back to the repository
        students.put(id, existingStudent); // Replace with appropriate database save logic if needed

        return existingStudent; // Return the updated student
    }


    // Delete a student
    public boolean deleteStudent(String id) {
        if (!students.containsKey(id)) {
            return false; // Student not found
        }
        students.remove(id);
        return true;
    }

    // Check if a student exists by name and email
    public boolean existsByEmail(String email) {
        return students.values().stream()
                .anyMatch(student -> student.getEmail().equalsIgnoreCase(email));
    }


}