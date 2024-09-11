package com.uzay.securityschool.school.controller;

import com.uzay.securityschool.school.entity.*;
import com.uzay.securityschool.school.repo.SchoolRepository;
import com.uzay.securityschool.school.repo.StudentLessonRepository;
import com.uzay.securityschool.school.repo.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SchoolController {

    private final SchoolRepository schoolRepository;
    private final TeacherRepository teacherRepository;
    private final StudentLessonRepository studentLessonRepository;

    public SchoolController(SchoolRepository schoolRepository, TeacherRepository teacherRepository, StudentLessonRepository studentLessonRepository) {
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
        this.studentLessonRepository = studentLessonRepository;
    }

    @PostMapping("/school-ekle")
    public ResponseEntity<?> addSchool(@RequestBody School school) {
        School save = schoolRepository.save(school);
        return ResponseEntity.ok().body("eklendi");

    }
    //okul eklerken teacherda ekliyor test için
    @PostMapping("/school-ekle2")
    public ResponseEntity<School> addSchool2(@RequestBody School school) {
        // Okulu kaydet
        School savedSchool = schoolRepository.save(school);

        // Eğer öğretmenler varsa, her bir öğretmenin okul referansını güncelle



        if (school.getTeacher() != null) {

            List<Teacher> teacherList = school.getTeacher();

            teacherList.stream().forEach(val->val.setSchool(savedSchool));
            teacherRepository.saveAll(school.getTeacher());


        }

        return ResponseEntity.ok(savedSchool);
    }
    @Transactional
    @DeleteMapping("/sil/{schoolId}")
    public ResponseEntity<?> deleteSchool2(@PathVariable Integer schoolId) {
        try {
            // School'u bul
            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new ResourceNotFoundException("School not found"));

            // Öğretmenlerin öğrenci ilişkilerini temizle
            school.getTeacher().forEach(teacher -> {
                teacher.getStudent().forEach(student -> {
                    student.getTeacher().remove(teacher); // Öğrenciden de öğretmeni kaldır
                });
                teacher.getStudent().clear(); // Öğretmenden öğrencileri temizle
                teacher.setSchool(null); // Öğretmenden okulu kaldır
            });

            // Öğrencilerin öğretmen ilişkilerini ve derslerini temizle
            school.getStudent().forEach(student -> {
                student.getTeacher().forEach(teacher -> {
                    teacher.getStudent().remove(student); // Öğretmenden öğrenciyi kaldır
                });
                student.getTeacher().clear(); // Öğrenciden öğretmenleri temizle
                student.getStudentLessons().clear(); // Öğrencinin derslerini temizle
                student.setSchool(null); // Öğrenciden okulu kaldır
            });

            // Derslerin StudentLesson ilişkilerini temizle
            school.getLesson().forEach(lesson -> {
                lesson.getStudentLessons().clear(); // Dersin öğrenci ilişkilerini temizle
                lesson.setSchool(null); // Dersten okulu kaldır
            });

            // School'u sil
            schoolRepository.delete(school);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("hata", "Veritabanı bütünlüğü hatası", "detay", e.getMessage()));
        }
    }


    @GetMapping("/school-teacher")
    public ResponseEntity<?> getTeachers() {
        School school = schoolRepository.findById(1).orElseThrow();
        List<Teacher> teachers = school.getTeacher();  // Teacher'lara erişim
        return ResponseEntity.ok().body(teachers);
    }




}
