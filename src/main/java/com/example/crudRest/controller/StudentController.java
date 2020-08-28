package com.example.crudRest.controller;

import com.example.crudRest.model.Student;
import com.example.crudRest.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    static final String MESSAGE = "Student not found on : ";

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentsById(@PathVariable(value = "id") Long studentId)
    throws Exception{
        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new Exception(MESSAGE + studentId));
        return ResponseEntity.ok(student);
    }

    @PostMapping("/students")
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable(value = "id") Long studentId, @Valid @RequestBody Student studentDetails)
            throws Exception {

        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new Exception(MESSAGE + studentId));

        student.setEmail(studentDetails.getEmail());
        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setPhone(studentDetails.getPhone());
        student.setAge(studentDetails.getAge());

        final Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/student/{id}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "id") Long studentId) throws Exception {
        Student student =
                studentRepository
                        .findById(studentId)
                        .orElseThrow(() -> new Exception(MESSAGE + studentId));

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
