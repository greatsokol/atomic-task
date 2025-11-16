package org.atomicmd.back.controller;

import lombok.RequiredArgsConstructor;
import org.atomicmd.back.model.dto.ErrorResponseDto;
import org.atomicmd.back.service.AuditService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.atomicmd.back.utils.Constants.DETAILS_CONSTRAINT;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private final AuditService auditService;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException ce) {
            if (DETAILS_CONSTRAINT.equals(ce.getConstraintName())) {
                String message = "Деталь с таким наименованием уже существует";
                auditService.saveAuditMessage(message);
                return new ResponseEntity<>(new ErrorResponseDto(message), HttpStatus.CONFLICT);
            } else if ("uc_master_number".equals(ce.getConstraintName())) {
                String message = "Мастер с таким номером уже существует";
                auditService.saveAuditMessage(message);
                return new ResponseEntity<>(new ErrorResponseDto(message), HttpStatus.CONFLICT);
            }
        }
        auditService.saveAuditMessage(e.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        auditService.saveAuditMessage(errors.toString());
        return new ResponseEntity<>(new ErrorResponseDto(errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(jakarta.validation.ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach(vio -> {
            String propertyPath = vio.getPropertyPath().toString();
            String errorMessage = vio.getMessage();
            errors.put(propertyPath, errorMessage);
        });
        auditService.saveAuditMessage(errors.toString());
        return new ResponseEntity<>(new ErrorResponseDto(errors.toString()), HttpStatus.BAD_REQUEST);
    }

}
