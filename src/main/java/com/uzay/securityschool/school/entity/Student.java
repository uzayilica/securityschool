package com.uzay.securityschool.school.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Entity

@Getter

@Setter

@AllArgsConstructor

@NoArgsConstructor

public class Student {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer studentId;

    private String studentName;

    private String studentYas;

    @ManyToOne
    @JoinColumn(name = "schoolId")
    @JsonBackReference("school-student")
    private School school;



    @OneToMany(mappedBy = "student")
    @JsonManagedReference("s-s")
    private List<StudentLesson> studentLessons; // Öğrencinin ders ilişkileri

    @JsonIgnore
    @ManyToMany(mappedBy = "student")
    List<Teacher> teacher;




}