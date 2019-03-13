package fr.laerce.cinema.service;

import fr.laerce.cinema.dao.StudentPageRepository;
import fr.laerce.cinema.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;



@Service
public class StudentService {
    @Autowired
    private StudentPageRepository studentPageRepository;

    public Page<Student> listByPage(Pageable pageable) {
        return studentPageRepository.findAll(pageable);
    }
}