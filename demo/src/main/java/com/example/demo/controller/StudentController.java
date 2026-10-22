package com.example.demo.controller;

import com.example.demo.dto.CreateStudentRequestDto;
import com.example.demo.dto.CreateStudentResponseDto;
import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.dto.UpdateStudentRequestDto;
import com.example.demo.dto.UpdateStudentResponseDto;
import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PostMapping
  public ResponseEntity<CreateStudentResponseDto> create(
      @Valid @RequestBody CreateStudentRequestDto studentReq) {
    CreateStudentResponseDto createdStudent = studentService.create(studentReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CreateStudentResponseDto> getById(@PathVariable Long id) {

    CreateStudentResponseDto studentResponse = studentService.getdById(id);

    return ResponseEntity.ok(studentResponse);
  }

  @GetMapping
  public ResponseEntity<List<CreateStudentResponseDto>> getAll() {
    List<CreateStudentResponseDto> studentResponse = studentService.getAll();

    return ResponseEntity.ok(studentResponse);
  }

  @PutMapping
  public ResponseEntity<UpdateStudentResponseDto> update(@RequestParam Long id,
      @RequestBody UpdateStudentRequestDto studentReq) {

    UpdateStudentResponseDto studentResponse = studentService.update(id, studentReq);


    return ResponseEntity.ok(studentResponse);
  }

  @DeleteMapping
  public ResponseEntity<String> delete(@RequestParam Long id) {
    studentService.delete(id);
    return ResponseEntity.noContent().build();

  }

  @PatchMapping("/delete-soft")
  public ResponseEntity<String> deleteStudentSoftly(@RequestParam Long id) {
    studentService.deleteStudentSoftly(id);

    return ResponseEntity.noContent().build();

  }

}
