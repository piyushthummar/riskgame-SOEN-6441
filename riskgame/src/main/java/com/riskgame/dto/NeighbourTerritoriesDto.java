package com.riskgame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NeighbourTerritoriesDto {

	private int id;
	private String countryName;
	private String countryNeighbourName;
	/**
	 * @param id
	 * @param countryName
	 * @param countryNeighbourName
	 */
	public NeighbourTerritoriesDto(int id, String countryName, String countryNeighbourName) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.countryNeighbourName = countryNeighbourName;
	}
	
	/**
	 * 
	 */
	public NeighbourTerritoriesDto() {
		super();
		
	}

}
