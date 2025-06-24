package com.leverx.lms.learningmanagementsystem.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "id",
            nullable = false,
            updatable = false,
            columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;
    @UpdateTimestamp
    @Column(updatable = false)
    private Instant lastUpdatedAt;
    @LastModifiedBy
    @Column(updatable = false)
    private String lastUpdatedBy;
}
