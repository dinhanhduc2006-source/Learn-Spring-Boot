package com.example.demo.repository;

import com.example.demo.entity.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository  extends JpaRepository<Student,Long> {
  Optional<Student> findByIdAndIsDeletedIsFalse(Long id);
  //Tìm kiếm duy nhất một học sinh theo mã định danh id với điều kiện học sinh đó chưa bị xóa mềm.
  List<Student> findAllByIsDeletedFalse();
  //Lấy toàn bộ danh sách học sinh trong hệ thống đang hoạt động (chưa bị xóa mềm)
  Boolean existsByEmail(String email);
}
