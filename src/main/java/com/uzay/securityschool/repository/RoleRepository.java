package com.uzay.securityschool.repository;

import com.uzay.securityschool.entity.Role;
import com.uzay.securityschool.entity.User;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {




}
