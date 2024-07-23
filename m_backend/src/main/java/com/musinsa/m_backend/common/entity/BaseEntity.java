package com.musinsa.m_backend.common.entity;

import jakarta.persistence.*;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {

    @Version
    public long version;
    @Column(name = "use_yn", nullable = false)
    private Boolean useYn;
    @CreatedDate
    @Column(name = "create_dt", nullable = false, updatable = false)
    private LocalDateTime createDt;
    @LastModifiedDate
    @Column(name = "modify_dt", nullable = false)
    private LocalDateTime modifyDt;
    @Column(name = "delete_dt")
    private LocalDateTime deleteDt;

    @PrePersist
    public void prePersist() {
        this.useYn = true;
    }

    @PreUpdate
    public void preUpdate() {
        this.useYn = true;
    }
}
