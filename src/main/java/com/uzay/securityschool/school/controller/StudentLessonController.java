package com.uzay.securityschool.school.controller;

import com.uzay.securityschool.school.entity.Lesson;
import com.uzay.securityschool.school.entity.Student;
import com.uzay.securityschool.school.entity.StudentLesson;
import com.uzay.securityschool.school.repo.LessonRepository;
import com.uzay.securityschool.school.repo.StudentLessonRepository;
import com.uzay.securityschool.school.repo.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentLessonController {
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;
    private final StudentLessonRepository studentLessonRepository;

    public StudentLessonController(StudentRepository studentRepository, LessonRepository lessonRepository, StudentLessonRepository studentLessonRepository) {
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
        this.studentLessonRepository = studentLessonRepository;
    }
    @GetMapping("ogrenci-dersleri/{studentId}/lessons")
    public ResponseEntity<List<?>> getStudentLessons(@PathVariable Integer studentId) {
        // Öğrenci var mı kontrolü
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Öğrenci bulunamadı"));

        // Öğrencinin derslerini al
        List<StudentLesson> studentLessons = studentLessonRepository.findLessonsByStudentId(studentId);

        // Lesson listesi oluştur
        List<Lesson> lessons = studentLessons.stream()
                .map(StudentLesson::getLesson)
                .collect(Collectors.toList());

        List<String> dersadlarilistesi = lessons.stream().map(Lesson::getLessonName).collect(Collectors.toList());

        return ResponseEntity.ok(dersadlarilistesi);
    }

}
