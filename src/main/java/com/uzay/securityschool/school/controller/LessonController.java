package com.uzay.securityschool.school.controller;

import com.uzay.securityschool.school.entity.Lesson;
import com.uzay.securityschool.school.entity.School;
import com.uzay.securityschool.school.repo.LessonRepository;
import com.uzay.securityschool.school.repo.SchoolRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessonController {

    private final LessonRepository lessonRepository;
    private final SchoolRepository schoolRepository;

    public LessonController(LessonRepository lessonRepository, SchoolRepository schoolRepository) {
        this.lessonRepository = lessonRepository;
        this.schoolRepository = schoolRepository;
    }


    @PostMapping("/lesson-ekle")
    public ResponseEntity<?> addResponseEntity(@RequestBody Lesson lesson) {
        if (lesson.getSchool() == null || lesson.getSchool().getSchoolId() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Integer schoolId = lesson.getSchool().getSchoolId();

        School school = schoolRepository.findById(schoolId).orElseThrow(() -> new ResourceNotFoundException("School not found"));

        lesson.setSchool(school);

        Lesson save = lessonRepository.save(lesson);
        return ResponseEntity.ok(save);


    }


















    @PostMapping("/lesson-ekle2")
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson) {
        // School nesnesi null ise, schoolId'ye göre School nesnesini al
        if (lesson.getSchool() == null || lesson.getSchool().getSchoolId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Integer schoolId = lesson.getSchool().getSchoolId();
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id " + schoolId));

        // Lesson nesnesine School nesnesini ayarla
        lesson.setSchool(school);

        // Lesson nesnesini kaydet
        Lesson save = lessonRepository.save(lesson);
        return ResponseEntity.ok(save);
    }



}
