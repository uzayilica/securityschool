package com.uzay.securityschool.school.repo;

import com.uzay.securityschool.school.entity.Lesson;
import com.uzay.securityschool.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface  StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT new map(st.studentName as studentName, st.school.schoolName as schoolName) FROM Student st JOIN st.school")
    List<Map<String, String>> getStudentNamesWithSchoolNames();



}
