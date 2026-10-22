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

  public Student create(Student studentReq) {
    studentReq.setDeleted(false);
    Student studentResponse= studentRepository.save(studentReq);

    return studentResponse;
  }

  public List<Student> getAll() {
    List<Student> studentList = studentRepository.findAllByIsDeletedFalse();
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

  public Student update(Long id, Student studentReq) {
    Optional<Student> existingStudent = studentRepository.findByIdAndIsDeletedIsFalse(id);

    if (existingStudent.isEmpty()) {
      return null;
    }

    Student studentToSave = existingStudent.get();

    studentToSave.setName(studentReq.getName());
    studentToSave.setAge(studentReq.getAge());
    studentToSave.setEmail(studentReq.getEmail());
    studentToSave.setSubject(studentReq.getSubject());
    studentToSave.setDeleted(false);


    return studentRepository.save(studentToSave);

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

  public Boolean deleteStudentSoftly(Long id){
    Optional<Student> existingStudent = studentRepository.findByIdAndIsDeletedIsFalse(id);

    if (existingStudent.isEmpty()){
      return  false;
    }

    Student studentToSave = existingStudent.get();
    studentToSave.setDeleted(true);
    studentRepository.save(studentToSave);

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
