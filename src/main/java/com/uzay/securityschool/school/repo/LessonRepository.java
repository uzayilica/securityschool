package com.uzay.securityschool.school.repo;

import com.uzay.securityschool.school.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository  extends JpaRepository<Lesson, Integer> {
}
