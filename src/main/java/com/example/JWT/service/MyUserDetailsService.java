package com.example.JWT.service;

import com.example.JWT.entity.Student;
import com.example.JWT.repository.StudentRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public MyUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new User(student.getUsername(), student.getPassword(), List.of(new SimpleGrantedAuthority(student.getRole())));
    }
}
