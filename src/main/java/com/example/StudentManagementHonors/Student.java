package com.example.StudentManagementHonors;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter

public class Student {
    String studentId;
    String name;
    String email;
    String phone;
    String password;
    String address;
}

