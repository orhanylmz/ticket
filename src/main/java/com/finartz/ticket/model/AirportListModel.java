package com.finartz.ticket.model;

import java.util.List;

import com.finartz.ticket.dto.AirportDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class AirportListModel {
	private List<AirportDTO> airportList;
}