package com.riskgame.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Country DTO which the data given by user side
 * 
 * @author <a href="mailto:z_tel@encs.concordia.ca">Zankhanaben Patel</a>
 */
@Getter
@Setter
@ToString
public class CountryDto {

	private int id;
	private String countryName;
	private String continentName;
	/**
	 * Parameterize constructor
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
	 * default constructor
	 */
	public CountryDto() {
		super();
		
	}

}
