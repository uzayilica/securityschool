package com.uzay.securityschool.school.controller;

import com.uzay.securityschool.school.entity.School;
import com.uzay.securityschool.school.entity.Student;
import com.uzay.securityschool.school.entity.Teacher;
import com.uzay.securityschool.school.repo.SchoolRepository;
import com.uzay.securityschool.school.repo.StudentRepository;
import com.uzay.securityschool.school.repo.TeacherRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class TeacherController {


    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepository;

    public TeacherController(TeacherRepository teacherRepository, SchoolRepository schoolRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping(value = "/teacher-ekle", consumes = "application/json", produces = "application/json")

    public ResponseEntity<?> addTeacher(@RequestBody Teacher teacher) {

        if (teacher.getSchool() != null) {
            // School kontrol edilir ve teacher objesine atanır
            School school = schoolRepository.findById(teacher.getSchool().getSchoolId())
                    .orElseThrow(() -> new ResourceNotFoundException("School not found"));
            teacher.setSchool(school);
        }

        Teacher savedTeacher = teacherRepository.save(teacher);
        return ResponseEntity.ok(savedTeacher);

    }

    @PostMapping("/teacher-ekle2")
    public ResponseEntity<Teacher> addTeacher2(@RequestBody Teacher teacher) {
        // Öğretmeni kaydetmeden önce school set ediliyor
        if (teacher.getSchool() != null && teacher.getSchool().getSchoolId() != null) {
            Optional<School> existingSchool = schoolRepository.findById(teacher.getSchool().getSchoolId());
            existingSchool.ifPresent(teacher::setSchool); // Okul atanıyor
        }

        // Öğretmeni kaydet
        Teacher savedTeacher = teacherRepository.save(teacher);

        // Öğrenci-öğretmen ilişkilerini kaydet
        if (teacher.getStudent() != null) {
            List<Student> studentList = teacher.getStudent();

            // Öğrencileri veritabanında bul ve ilişkileri güncelle
            List<Student> existingStudents = studentList.stream()
                    .map(student -> studentRepository.findById(student.getStudentId()))
                    .filter(Optional::isPresent) // Var olan öğrencileri filtrele
                    .map(Optional::get) // Optional'dan öğrenci nesnelerini al
                    .collect(Collectors.toList());

            // Her var olan öğrenci için ilişkileri güncelle
            existingStudents.forEach(val -> {
                // Öğrenciyi öğretmene ekle
                val.getTeacher().add(savedTeacher);
                studentRepository.save(val);
            });
        }

        return ResponseEntity.ok(savedTeacher);
    }
}
