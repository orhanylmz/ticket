package com.finartz.ticket.model;

import java.util.List;

import com.finartz.ticket.dto.FlyDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class FlyListModel {
	private List<FlyDTO> flyList;
}