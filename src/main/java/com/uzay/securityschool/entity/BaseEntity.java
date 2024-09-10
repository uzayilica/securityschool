package com.uzay.securityschool.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @Column(updatable = false)
    @CreatedDate
    private String created_At;

    @Column(insertable = false)
    @LastModifiedDate
    private String modified_At;

    @Column(updatable = false)
    @CreatedBy
    private String created_By;


    @Column(insertable = false)
    @LastModifiedBy
    private String modified_By;



}
