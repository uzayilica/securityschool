package com.uzay.securityschool.school.controller;

import com.uzay.securityschool.school.entity.School;
import com.uzay.securityschool.school.entity.Student;
import com.uzay.securityschool.school.repo.SchoolRepository;
import com.uzay.securityschool.school.repo.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class QueryTestController {


    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;

    public QueryTestController(SchoolRepository schoolRepository, StudentRepository studentRepository) {
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("tum-okul-adlari")
    public ResponseEntity<?> tumOkulAdlari() {
        List<String> allOkul = schoolRepository.findAllOkulAd();
        return ResponseEntity.ok(allOkul);
    }

    @GetMapping("tum-okullar")
    public ResponseEntity<?> tumOkullar() {
        List<School> allOkul = schoolRepository.findAllOkul();
        return ResponseEntity.ok(allOkul);
    }
    @GetMapping("/iguBilgileri")
    public ResponseEntity<?> iguBilgileri() {
        School igu = schoolRepository.iguBilgileri();
        return ResponseEntity.ok(igu);
    }
    @GetMapping("okul/{schoolName}")
    public ResponseEntity<?> iguBilgileri(@PathVariable String schoolName) {
        School school = schoolRepository.girilenIsmeGoreOkulBilgisi(schoolName).orElse(null);
        return ResponseEntity.ok(school);
    }


    @GetMapping("studentVeGitikleriOkul")
    public ResponseEntity<?> StudentNameVeSchoolName () {
        List<Map<String, String>> studentNamesWithSchoolNames = studentRepository.getStudentNamesWithSchoolNames();
        return ResponseEntity.ok(studentNamesWithSchoolNames);
    }

}
