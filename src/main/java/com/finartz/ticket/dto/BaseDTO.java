package com.finartz.ticket.dto;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseDTO {
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	String createdBy;
	String updatedBy;
}
