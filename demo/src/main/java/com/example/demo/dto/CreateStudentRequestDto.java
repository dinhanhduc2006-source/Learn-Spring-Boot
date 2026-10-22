package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateStudentRequestDto {

  @NotBlank(message = "Name cannot be null/Empty or blank")
  @Size(min = 2,max = 50,message = "Student name must be within 2 to 50 character long")
  private String name;

  @NotNull(message = "Age is required")
  @Min(value = 18, message = "Student must be atleast 18 years old")
  private Integer age;

  @NotBlank(message = "Student email cannot be blank")
  @Email(message = "Student email must be valid")
  private String email;

  @NotBlank(message = "Subject is required")
  private String subject;

  @NotNull(message = "RollNo is required")
  private Integer rollNo;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Integer getRollNo() {
    return rollNo;
  }

  public void setRollNo(Integer rollNo) {
    this.rollNo = rollNo;
  }
}
