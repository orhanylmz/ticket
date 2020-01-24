package com.finartz.ticket.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.finartz.ticket.entity.AirlineEntity;
import com.finartz.ticket.entity.AirplaneEntity;
import com.finartz.ticket.entity.AirportEntity;
import com.finartz.ticket.entity.FlywayEntity;
import com.finartz.ticket.service.AirlineService;
import com.finartz.ticket.service.AirplaneService;
import com.finartz.ticket.service.AirportService;
import com.finartz.ticket.service.FlywayService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
	private final AirportService airportService;
	private final FlywayService flywayService;
	private final AirlineService airlineService;
	private final AirplaneService airplaneService;

	@Override
	public void run(String... args) throws Exception {
		AirportEntity trbAirport = new AirportEntity();
		trbAirport.setCode("TRB");
		trbAirport.setCountry("TÜRKİYE");
		trbAirport.setProvince("TRABZON");
		trbAirport.setName("TRABZON HAVALİMANI");
		trbAirport = airportService.save(trbAirport);

		AirportEntity sawAirport = new AirportEntity();
		sawAirport.setCode("SAW");
		sawAirport.setCountry("TÜRKİYE");
		sawAirport.setProvince("İSTANBUL");
		sawAirport.setName("SABİHA GÖKÇEN HAVALİMANI");
		sawAirport = airportService.save(sawAirport);

		FlywayEntity trb_saw = new FlywayEntity();
		trb_saw.setFromAirport(trbAirport);
		trb_saw.setToAirport(sawAirport);
		trb_saw = flywayService.save(trb_saw);

		FlywayEntity saw_trb = new FlywayEntity();
		saw_trb.setFromAirport(sawAirport);
		saw_trb.setToAirport(trbAirport);
		saw_trb = flywayService.save(saw_trb);

		AirlineEntity pegasus = new AirlineEntity();
		pegasus.setName("PEGASUS");
		pegasus = airlineService.save(pegasus);

		AirlineEntity thy = new AirlineEntity();
		thy.setName("TÜRK HAVAYOLLARI");
		thy = airlineService.save(thy);

		AirplaneEntity airplane01 = new AirplaneEntity();
		airplane01.setAirline(pegasus);
		airplane01.setName("airplane01");
		airplane01 = airplaneService.save(airplane01);
		
		AirplaneEntity airplane02 = new AirplaneEntity();
		airplane02.setAirline(thy);
		airplane02.setName("airplane02");
		airplane02 = airplaneService.save(airplane02);
		
		AirplaneEntity airplane03 = new AirplaneEntity();
		airplane03.setAirline(thy);
		airplane03.setName("airplane03");
		airplane03 = airplaneService.save(airplane03);
	}
}
