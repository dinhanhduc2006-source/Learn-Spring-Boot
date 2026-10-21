package com.example.demo.service;

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
    Student studentResp = studentRepository.save(studentReq);
    return studentResp;
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

  public Student update(Long id, Student studentReq) {
    Optional<Student> existingStudent = studentRepository.findById(id);

    if (existingStudent.isEmpty()) {
      return null;
    }

    Student studentToSave = existingStudent.get();
    studentToSave.setName(studentReq.getName());
    studentToSave.setAge(studentReq.getAge());
    studentToSave.setEmail(studentToSave.getEmail());
    studentToSave.setSubject(studentReq.getSubject());
    studentToSave.setRollNumber(studentReq.getRollNumber());

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

}
