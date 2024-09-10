package com.uzay.securityschool.repository;

import com.uzay.securityschool.entity.User;
import com.uzay.securityschool.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);



}
