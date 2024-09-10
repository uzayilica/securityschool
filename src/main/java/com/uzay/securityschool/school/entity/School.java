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
@Entity @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schoolId;

    private String schoolName;

    private Integer kapasite;


@JsonManagedReference("teacher-school")
    @OneToMany(mappedBy = "school",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Teacher> teacher;


    @OneToMany(mappedBy = "school",orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonManagedReference("school-student")
    private List<Student> student;




    @JsonManagedReference("school-lesson")
    @OneToMany(mappedBy = "school",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<Lesson> lesson;



}
