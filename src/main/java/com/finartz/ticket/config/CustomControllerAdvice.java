package com.finartz.ticket.config;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.finartz.ticket.exception.AirplaneNotFoundException;
import com.finartz.ticket.exception.CheckinAlreadyExistException;
import com.finartz.ticket.exception.CustomEntityNotFoundException;
import com.finartz.ticket.exception.CustomRuntimeException;
import com.finartz.ticket.exception.FlyNotFoundException;
import com.finartz.ticket.exception.InsufficientSeatException;
import com.finartz.ticket.exception.TicketNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomControllerAdvice {
	@ExceptionHandler(AirplaneNotFoundException.class)
	public ResponseEntity<String> handleAirplaneNotFoundException(AirplaneNotFoundException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomEntityNotFoundException.class)
	public ResponseEntity<String> handleCustomEntityNotFoundException(CustomEntityNotFoundException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FlyNotFoundException.class)
	public ResponseEntity<String> handleFlyNotFoundException(FlyNotFoundException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InsufficientSeatException.class)
	public ResponseEntity<String> handleInsufficientSeatException(InsufficientSeatException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CheckinAlreadyExistException.class)
	public ResponseEntity<String> handleCheckinAlreadyExistException(CheckinAlreadyExistException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<String> handleTicketNotFoundException(TicketNotFoundException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>("Already exist", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<String> handleMissingPathVariableException(MissingPathVariableException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>("Please check your inputs", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomRuntimeException.class)
	public ResponseEntity<String> handleCustomRuntimeException(CustomRuntimeException exception) {
		log.info("Handle " + exception.getClass().getName());
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
