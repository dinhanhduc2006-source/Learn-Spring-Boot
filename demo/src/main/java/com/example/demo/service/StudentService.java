package com.example.demo.service;

import com.example.demo.dto.StudentRequestDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

  private StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public StudentResponseDto create(StudentRequestDto studentReq) {
    Student student = studentRepository.save(this.mapToEntity(studentReq));
    StudentResponseDto studentResponse = this.mapToResponseDTO(student,"da tao xong");
    return studentResponse;
  }

  public List<Student> getAll() {
    List<Student> studentList = studentRepository.findAll();
    return studentList;
  }

  public Student getdById(Long id) {
    Optional<Student> studentResponse = studentRepository.findById(id);
    //Optional: Một lớp trong Java dùng để biểu diễn giá trị có thể tồn tại hoặc không.
    //thuong khi tim kiem 1 cai gi do co the ko tim thay hoac tim thay
    if (studentResponse.isPresent() /*kiem tra xem co gia tri khong */) {
      return studentResponse.get();
    }
    return null;
  }

  public StudentResponseDto update(Long id, StudentRequestDto studentReq) {
    Optional<Student> existingStudent = studentRepository.findById(id);

    if (existingStudent.isEmpty()) {
      return null;
    }

    Student studentToSave = existingStudent.get();
    studentToSave.setName(studentReq.getName());
    studentToSave.setAge(studentReq.getAge());
    studentToSave.setEmail(studentReq.getEmail());
    studentToSave.setSubject(studentReq.getSubject());

    Student student=studentRepository.save(studentToSave);

    StudentResponseDto savedStudent = this.mapToResponseDTO(student,"successfull");

    return savedStudent;

  }


  public Boolean delete(Long id) {
    //Xem co doi tuong sinh vinh vs id nay khong, tranh ko co thi loi code
    Boolean isStudent = studentRepository.existsById(id);

    if (!isStudent) {
      return false;
    }

    studentRepository.deleteById(id);

    return true;
  }

  private Student mapToEntity(StudentRequestDto studentReq) {
    Student student = new Student();

    student.setName(studentReq.getName());
    student.setSubject(studentReq.getSubject());
    student.setAge(studentReq.getAge());
    student.setEmail(studentReq.getEmail());

    return student;
  }

  private StudentResponseDto mapToResponseDTO(Student student, String message) {
    StudentResponseDto studentResponseDto = new StudentResponseDto();
    studentResponseDto.setAge(student.getAge());
    studentResponseDto.setEmail(student.getEmail());
    studentResponseDto.setName(student.getName());
    studentResponseDto.setSubject(student.getSubject());
    studentResponseDto.setId(student.getId());
    studentResponseDto.setMessage(message);
    return studentResponseDto;
  }

}
