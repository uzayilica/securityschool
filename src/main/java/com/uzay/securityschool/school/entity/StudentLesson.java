package com.uzay.securityschool.school.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student_lesson") // Tablo adı
public class StudentLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Birincil anahtar

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "studentId")
    @JsonBackReference("s-s")
    private Student student; // Öğrenci ID referansı

    @ManyToOne
    @JsonBackReference("l-s")
    @JoinColumn(name = "lessonId", referencedColumnName = "lessonId")
    private Lesson lesson; // Ders ID referansı
}