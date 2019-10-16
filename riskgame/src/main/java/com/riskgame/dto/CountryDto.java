package com.riskgame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CountryDto {

	private int id;
	private String countryName;
	private String continentName;
	/**
	 * @param id
	 * @param countryName
	 * @param continentName
	 */
	public CountryDto(int id, String countryName, String continentName) {
		super();
		this.id = id;
		this.countryName = countryName;
		this.continentName = continentName;
	}
	
	/**
	 * 
	 */
	public CountryDto() {
		super();
		
	}

}
