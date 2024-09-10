package com.uzay.securityschool.school.controller;

import com.uzay.securityschool.school.entity.School;
import com.uzay.securityschool.school.entity.Student;
import com.uzay.securityschool.school.entity.StudentLesson;
import com.uzay.securityschool.school.repo.SchoolRepository;
import com.uzay.securityschool.school.repo.StudentLessonRepository;
import com.uzay.securityschool.school.repo.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {


    private final StudentRepository studentRepository;
    private final StudentLessonRepository studentLessonRepository;
    private final SchoolRepository schoolRepository;

    public StudentController(StudentRepository studentRepository, StudentLessonRepository studentLessonRepository, SchoolRepository schoolRepository) {
        this.studentRepository = studentRepository;
        this.studentLessonRepository = studentLessonRepository;
        this.schoolRepository = schoolRepository;
    }

    @PostMapping("/student-ekle")
    public ResponseEntity<?> addSchool(@RequestBody Student student) {

        Integer schoolId = student.getSchool().getSchoolId();
        Optional<School> schoolRepositoryById = schoolRepository.findById(schoolId);
        if (schoolRepositoryById.isPresent()) {
            School school = schoolRepositoryById.get();
            student.setSchool(school);
            Student studentnew = studentRepository.save(student);
            return ResponseEntity.ok(studentnew);

        }
        else {
            return ResponseEntity.ok().body("kullanıcı bulunamadı");

        }


    }



//    @GetMapping("/ogrenci/{id}")
//    public ResponseEntity<?> ogrenicininAldigiDersler(@PathVariable Integer id) {
//        List<StudentLesson> lessonsByStudent = studentLessonRepository.findLessonsByStudentId(id);
//
//        if (lessonsByStudent.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(lessonsByStudent);
//
//    }
//

    @PostMapping("/student-ekle2")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        // Öğrenciyi kaydet
        Student savedStudent = studentRepository.save(student);

        // Öğrenci ile ilişkilendirilen dersleri ekle
        if (student.getStudentLessons() != null) {
            for (StudentLesson studentLesson : student.getStudentLessons()) {
                studentLesson.setStudent(savedStudent);
                studentLessonRepository.save(studentLesson);
            }
        }

        return ResponseEntity.ok(savedStudent);
    }

}
