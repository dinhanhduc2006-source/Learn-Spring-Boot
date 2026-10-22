package com.example.demo.exception;

import com.example.demo.dto.ExceptionResponseDto;
import com.example.demo.dto.ValidationExceptionResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHanderException {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(
      ResourceNotFoundException ex,
      HttpServletRequest request) {

    ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
        LocalDateTime.now(),
        HttpStatus.NOT_FOUND.value(),
        HttpStatus.NOT_FOUND.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDto);
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ExceptionResponseDto> handleDuplicateResourceException(
      DuplicateResourceException ex, HttpServletRequest request) {
    ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
        LocalDateTime.now(),
        HttpStatus.CONFLICT.value(),
        HttpStatus.CONFLICT.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDto);
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponseDto> handleRunTimeException(RuntimeException ex,
      HttpServletRequest request) {
    ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
        LocalDateTime.now(),
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
        ex.getMessage(),
        request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponseDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationExceptionResponseDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, HttpServletRequest request) {

    Map<String, String> fieldErrors = new HashMap<>();

    ex.getBindingResult().getFieldErrors()
        .forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage()));

    ValidationExceptionResponseDto exceptionResponse = new ValidationExceptionResponseDto(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        HttpStatus.BAD_REQUEST.getReasonPhrase(),
        "Validation Failed",
        request.getRequestURI(),
        fieldErrors
    );

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exceptionResponse);
  }
}


