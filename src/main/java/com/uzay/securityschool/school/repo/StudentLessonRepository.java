package com.uzay.securityschool.school.repo;


import com.uzay.securityschool.school.entity.School;
import com.uzay.securityschool.school.entity.StudentLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentLessonRepository extends JpaRepository<StudentLesson, Long> {
    // Gerekirse özel sorgular ekleyebilirsiniz


    @Query("SELECT sl FROM StudentLesson sl " +
            "INNER JOIN sl.student s " +
            "INNER JOIN sl.lesson l " +
            "WHERE s.studentId = :studentId")
    List<StudentLesson> findLessonsByStudentId(@Param("studentId") Integer studentId);


    List<StudentLesson> findByLessonSchool(School school);
}




