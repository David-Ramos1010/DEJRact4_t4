package com.davidramos1010.crudrapido.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidramos1010.crudrapido.entity.Student;
import com.davidramos1010.crudrapido.service.StudentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // GET /api/v1/students
    @GetMapping
    public List<Student> getAll() {
        return studentService.getStudents();
    }

    // GET /api/v1/students/{studentId}
    @GetMapping("/{studentId}")
    public Student getById(@PathVariable Long studentId) {
        return studentService.getStudent(studentId);
    }

    // POST /api/v1/students
    @PostMapping
    public Student save(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
        return student;
    }

    // PUT /api/v1/students/{studentId}
    @PutMapping("/{studentId}")
    public void update(@PathVariable Long studentId, @RequestBody Student student) {
        student.setStudentId(studentId);
        studentService.saveOrUpdate(student);
    }

    // DELETE /api/v1/students/{studentId}
    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
    }
}