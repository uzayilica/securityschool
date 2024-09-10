package com.uzay.securityschool.school.repo;

import com.uzay.securityschool.school.entity.Lesson;
import com.uzay.securityschool.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  TeacherRepository  extends JpaRepository<Teacher, Integer> {
}
