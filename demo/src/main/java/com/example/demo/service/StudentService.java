package com.example.demo.service;

import com.example.demo.dto.CreateStudentRequestDto;
import com.example.demo.dto.CreateStudentResponseDto;
import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.dto.UpdateStudentRequestDto;
import com.example.demo.dto.UpdateStudentResponseDto;
import com.example.demo.entity.Student;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.StudentRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public CreateStudentResponseDto create(CreateStudentRequestDto studentReq) {

    Student student = this.mapToEntity(studentReq);

    if (emailExists(student)) {
      throw new DuplicateResourceException("Student with email " + student.getEmail()
          + " already exists");
    }

    Student studentResponse = studentRepository.save(student);

    return this.mapToDto(studentResponse);
  }

  public List<CreateStudentResponseDto> getAll() {
    List<Student> studentList = studentRepository.findAllByIsDeletedFalse();
    return studentList.stream()
        .map(this::mapToDto)
        .toList();
  }

  public CreateStudentResponseDto getdById(Long id) {
    Student studentResponse = studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

    return mapToDto(studentResponse);
  }

  public UpdateStudentResponseDto update(Long id, UpdateStudentRequestDto studentReq) {
    Student existingStudent = studentRepository.findByIdAndIsDeletedIsFalse(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

    existingStudent.setName(studentReq.getName());
    existingStudent.setRollNo(studentReq.getRollNo());
    existingStudent.setSubject(studentReq.getSubject());
    existingStudent.setAge(studentReq.getAge());
    existingStudent.setDeleted(false);
    existingStudent.setUpdatedAt(LocalDateTime.now());

    Student savedStudent = studentRepository.save(existingStudent);

    return mapToUpdateDto(savedStudent);

  }


  public void delete(Long id) {
    Student studentToBeDeleted = studentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));

    studentRepository.deleteById(id);
  }

  public void deleteStudentSoftly(Long id) {
    Student studentToBeDeleted = studentRepository
        .findByIdAndIsDeletedIsFalse(id)
        .orElseThrow(() ->
            new ResourceNotFoundException("Student with id " + id + " not found"));

    studentToBeDeleted.setDeleted(true);
    studentRepository.save(studentToBeDeleted);
  }

  private Student mapToEntity(CreateStudentRequestDto studentReqDto) {
    Student student = new Student();

    student.setName(studentReqDto.getName());
    student.setAge(studentReqDto.getAge());
    student.setEmail(studentReqDto.getEmail());
    student.setRollNo(studentReqDto.getRollNo());
    student.setSubject(studentReqDto.getSubject());
    student.setCreatedAt(LocalDateTime.now());
    student.setUpdatedAt(LocalDateTime.now());

    student.setDeleted(false);

    return student;
  }

  private CreateStudentResponseDto mapToDto(Student student) {
    CreateStudentResponseDto responseDto = new CreateStudentResponseDto();

    responseDto.setId(student.getId());
    responseDto.setName(student.getName());
    responseDto.setAge(student.getAge());
    responseDto.setEmail(student.getEmail());
    responseDto.setRollNo(student.getRollNo());
    responseDto.setSubject(student.getSubject());
    responseDto.setMessage("Student saved successfully");
    responseDto.setCreatedAt(student.getCreatedAt());
    responseDto.setUpdatedAt(student.getUpdatedAt());

    return responseDto;

  }

  private UpdateStudentResponseDto mapToUpdateDto(Student student) {
    UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();

    responseDto.setId(student.getId());
    responseDto.setName(student.getName());
    responseDto.setAge(student.getAge());
    responseDto.setEmail(student.getEmail());
    responseDto.setRollNo(student.getRollNo());
    responseDto.setSubject(student.getSubject());
    responseDto.setMessage("Student updated successfully");
    responseDto.setUpdatedAt(student.getUpdatedAt());

    return responseDto;
  }

  private boolean emailExists(Student student) {
    return studentRepository.existsByEmail(student.getEmail());
  }

}
