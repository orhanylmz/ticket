package com.finartz.ticket.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.finartz.ticket.util.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
	@CreatedDate
	@Column(name = "created_at")
	LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	LocalDateTime updatedAt;

	@PrePersist
	void prePersist() {
		createdAt = updatedAt = DateUtil.now();
	}

	@PreUpdate
	void preUpdate() {
		updatedAt = DateUtil.now();
	}
}
