package com.uzay.securityschool.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("mesaj", fieldError.getDefaultMessage());
            errorDetails.put("hatalı alan", fieldError.getField());
            errorDetails.put("hatalı değer", fieldError.getRejectedValue().toString());
            errors.add(errorDetails);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("hata", "zaten mevcut");
            errorDetails.put("InvalidValue", constraintViolation.getInvalidValue().toString());
            errorDetails.put("hatali path", constraintViolation.getPropertyPath().toString());
            errors.add(errorDetails);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> errorDetails = new HashMap<>();

        // Örnek olarak SQL hata mesajını analiz edelim
        String message = ex.getMessage();
        String fieldName = extractFieldName(message);
        String errorMessage = "Veritabanı bütünlüğü hatası";

        if (fieldName != null) {
            errorMessage = "Bu " + fieldName + " alanında mevcut olan bir kayıt hatası";
        }

        errorDetails.put("hata", errorMessage);
        errorDetails.put("detay", message);

        return ResponseEntity.badRequest().body(errorDetails);
    }

    // Örnek olarak kısıtlama adını çıkaran bir yardımcı yöntem
    private String extractFieldName(String message) {
        if (message.contains("Key")) {
            // Basit bir örnek: mesajdan anahtar alanı çıkarma
            int start = message.indexOf("Key") + 4; // "Key (" sonrası başlar
            int end = message.indexOf(")", start);
            return message.substring(start, end);
        }
        return null;
    }


}
