package com.example.demo.controller;

import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

  private StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping("/create")
  public ResponseEntity<StudentResponseDto> create(@RequestBody StudentRequestDto studentReq) {
    StudentResponseDto createdStudent = studentService.create(studentReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
  }

  @GetMapping("/get")
  public ResponseEntity<Student> getById(@RequestParam Long id) {
    Student studentResponse = studentService.getdById(id);

    if (studentResponse == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(studentResponse);
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<Student>> getAll() {
    List<Student> studentResponse = studentService.getAll();

    if (studentResponse.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(studentResponse);
  }

  @PutMapping("/update")
  public ResponseEntity<StudentResponseDto> update(@RequestParam Long id,
      @RequestBody StudentRequestDto studentReq) {
    StudentResponseDto studentResponse = studentService.update(id, studentReq);

    if (studentResponse == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(studentResponse);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> delete(@RequestParam Long id) {
    Boolean isDeleted = studentService.delete(id);

    if (!isDeleted) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok("Record deleted");
  }

}
