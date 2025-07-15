package com.example.JWT.service;

import com.example.JWT.config.AuthRequest;
import com.example.JWT.entity.Student;
import com.example.JWT.enums.Role;
import com.example.JWT.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Student signUpUser(AuthRequest request) {
        if (studentRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("User already exists with username: " + request.getUsername());
        }

        Student student = new Student();
        student.setUsername(request.getUsername());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setRole(Role.STUDENT);

        return studentRepository.save(student);
    }
}
