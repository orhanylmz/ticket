package com.finartz.ticket.util;

import javax.annotation.Priority;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.finartz.ticket.dto.AirlineDTO;
import com.finartz.ticket.dto.AirplaneDTO;
import com.finartz.ticket.dto.AirportDTO;
import com.finartz.ticket.dto.BaseDTO;
import com.finartz.ticket.dto.CustomerDTO;
import com.finartz.ticket.dto.FlyDTO;
import com.finartz.ticket.dto.FlywayDTO;
import com.finartz.ticket.dto.TicketDTO;
import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.AirplaneEntity;
import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.BaseEntity;
import com.finartz.ticket.entity.CustomerEntity;
import com.finartz.ticket.entity.FlyEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.entity.TicketEntity;

@Priority(value = 0)
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class CommonMapper {
	public abstract BaseDTO mapEntityToDto(BaseEntity entity);

	public abstract BaseEntity mapDtoToEntity(BaseDTO dto);

	public abstract void updateEntity(BaseDTO dto, @MappingTarget BaseEntity entity);

	public abstract AirlineDTO mapEntityToDto(AirlineEntity entity);

	public abstract AirlineEntity mapDtoToEntity(AirlineDTO dto);

	public abstract void updateEntity(AirlineDTO dto, @MappingTarget AirlineEntity entity);

	public abstract AirplaneDTO mapEntityToDto(AirplaneEntity entity);

	public abstract AirplaneEntity mapDtoToEntity(AirplaneDTO dto);

	public abstract void updateEntity(AirplaneDTO dto, @MappingTarget AirplaneEntity entity);

	public abstract AirportDTO mapEntityToDto(AirportEntity entity);

	public abstract AirportEntity mapDtoToEntity(AirportDTO dto);

	public abstract void updateEntity(AirportDTO dto, @MappingTarget AirportEntity entity);

	public abstract CustomerDTO mapEntityToDto(CustomerEntity entity);

	public abstract CustomerEntity mapDtoToEntity(CustomerDTO dto);

	public abstract void updateEntity(CustomerDTO dto, @MappingTarget CustomerEntity entity);

	public abstract FlyDTO mapEntityToDto(FlyEntity entity);

	public abstract FlyEntity mapDtoToEntity(FlyDTO dto);

	public abstract void updateEntity(FlyDTO dto, @MappingTarget FlyEntity entity);

	public abstract FlywayDTO mapEntityToDto(FlywayEntity entity);

	public abstract FlywayEntity mapDtoToEntity(FlywayDTO dto);

	public abstract void updateEntity(FlywayDTO dto, @MappingTarget FlywayEntity entity);

	public abstract TicketDTO mapEntityToDto(TicketEntity entity);

	public abstract TicketEntity mapDtoToEntity(TicketDTO dto);

	public abstract void updateEntity(TicketDTO dto, @MappingTarget TicketEntity entity);

}