package com.uzay.securityschool.school.controller;

import com.uzay.securityschool.school.entity.School;
import com.uzay.securityschool.school.entity.Student;
import com.uzay.securityschool.school.entity.Teacher;
import com.uzay.securityschool.school.repo.SchoolRepository;
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

    public SchoolController(SchoolRepository schoolRepository, TeacherRepository teacherRepository) {
        this.schoolRepository = schoolRepository;
        this.teacherRepository = teacherRepository;
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
    public ResponseEntity<?> deleteSchool(@PathVariable Integer schoolId) {
        try {
            School school = schoolRepository.findById(schoolId)
                    .orElseThrow(() -> new ResourceNotFoundException("School not found"));

            // Öğretmenlerin school referansını temizle
            school.getTeacher().forEach(teacher -> teacher.setSchool(null));

            // Öğrencilerin school referansını temizle ve ilişkili StudentLesson kayıtlarını sil
            school.getStudent().forEach(student -> {
                student.setSchool(null);
                student.getStudentLessons().clear();
            });

            // Derslerin school referansını temizle ve ilişkili StudentLesson kayıtlarını sil
            school.getLesson().forEach(lesson -> {
                lesson.setSchool(null);
                lesson.getStudentLessons().clear();
            });

            // School'u sil
            schoolRepository.delete(school);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("hata", "Veritabanı bütünlüğü hatası", "detay", e.getMessage()));
        }
    }

}
