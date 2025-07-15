package com.example.JWT.service;

import com.example.JWT.config.AuthRequest;
import com.example.JWT.entity.Student;
import com.example.JWT.enums.Role;
import com.example.JWT.repository.StudentRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public MyUserDetailsService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signUpUser(AuthRequest request) {
        if (studentRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists with username: " + request.getUsername());
        }

        Student student = new Student();
        student.setUsername(request.getUsername());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setRole(Role.STUDENT);

        studentRepository.save(student);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new User(student.getUsername(), student.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + student.getRole().name())));
    }
}
