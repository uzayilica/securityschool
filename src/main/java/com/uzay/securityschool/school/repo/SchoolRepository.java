package com.uzay.securityschool.school.repo;

import com.uzay.securityschool.school.entity.Lesson;
import com.uzay.securityschool.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Integer>
{

    @Query("SELECT s.schoolName from  School s")
    List<String> findAllOkulAd();


    @Query("SELECT s from  School s")
    List<School> findAllOkul();

    @Query("SELECT s from  School s where  s.schoolName='igü'")
    School iguBilgileri();

    @Query("Select s from School s where s.schoolName=:schoolName")
    Optional<School> girilenIsmeGoreOkulBilgisi(@Param("schoolName") String schoolName);








}
